package com.limewire.game.data;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	private OrthographicCamera camera;
	Texture gridTex, selectionTex, moveDisplayTex, pTurnTex, eTurnTex;
	int squareSize = 34; // Pixel size of a square (including the black border)
	public static final int gridWidth = 8; // Width of the grid
	public static final int gridHeight = 8; // Height of the grid

	// Selection variables
	int selectionX = 0;
	int selectionY = 0;
	boolean showSelection = false;
	boolean isShipSelected = false;
	Set<Coords> moveSquares = new HashSet<Coords>();

	// Map and contents variables
	Map map;
	ArrayList<Ship> playerShips;
	ArrayList<Ship> enemyShips;

	// Game logic variables
	int turn = 0; // 0 means player turn, 1 & 2 are other colleges

	// These variables are the offset of the camera to show the corner map in the bottom left of the screen
	float cameraOffsetX;
	float cameraOffsetY;



	@Override
	public void create (){
		// Setup/calibrate camera
	    cameraOffsetX = Gdx.graphics.getWidth() / 2f;
	    cameraOffsetY = Gdx.graphics.getHeight() / 2f;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);
		camera.position.set(cameraOffsetX, cameraOffsetY, 0);
		batch = new SpriteBatch();


		// Load textures
		gridTex = new Texture("32gridSquare.png");
		selectionTex = new Texture("32selection.png");
		moveDisplayTex = new Texture("32moveDisplay.png");
		pTurnTex = new Texture("24playerTurn.png");
		eTurnTex = new Texture("24enemyTurn.png");


		// Create data structures for map and list of ships
		map = new Map(); // Map by default is all water
		playerShips = new ArrayList<Ship>();
		enemyShips = new ArrayList<Ship>();

		// Add some placeholder ships (for testing)
		playerShips.add(new Ship(1,1,1,"player"));
		playerShips.add(new Ship(3,4,1,"player"));
		enemyShips.add(new Ship(6,2, 1, "enemy"));
		enemyShips.add(new Ship(6,6, 1, "enemy"));

		// Add data to map squares for where ships are
		for (Ship ship: playerShips){
			map.getGrid()[ship.getX()][ship.getY()].ship = ship;
		}
		for (Ship ship: enemyShips){
			map.getGrid()[ship.getX()][ship.getY()].ship = ship;
		}
	}

	@Override
	public void render () {
        super.render();

        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		drawAssets();
		batch.end();

        moveSquares = getMoveSquares();
        isShipSelected = isShipSelected();
        handleInput();
    }

	public void handleSelection(){
		Coords coordinates = getGridLocation();

		// Handle cases where a ship is selected
		if (isShipSelected()){
			// Move the ship to a new location
			if (map.isValidSquare(coordinates)){
				moveSelectedShip(coordinates);
			}

			// If the location contains an enemy ship (to attack)
			else if (map.getShip(coordinates) != null){
				Ship newSelectedShip = map.getShip(coordinates);
				// If the new location contains an enemy ship
				if (!newSelectedShip.getTeam().equals("player")){
					// If the ship can attack
					if (getSelectedShip().canAttack()){
						// Check if the play is next to the ship (for a valid attack)
						if (getSelectedShip().nextToShip(newSelectedShip)){
							// Successfully attack the ship
							getSelectedShip().decAttacksLeft();
							deleteShip(newSelectedShip);
						}
					}

				}
			}
			showSelection = false;
		}
		// If the player selects the same square, invert the selection (to un-select it or re-select)
		else if (coordinates.equals(new Coords(selectionX, selectionY))) {
			showSelection = !showSelection;
		}
		else {
			showSelection = true;
		}
		selectionX = coordinates.getX();
		selectionY = coordinates.getY();
	}

	public void moveSelectedShip(Coords coords) {
		Ship ship = getSelectedShip();
		for (Coords moveSquare : moveSquares) {
			if (coords.equals(moveSquare)) {
				map.deleteShip(ship.getCoords());
				ship.decMovesLeft(Math.abs(ship.getX()-coords.getX()) + Math.abs(ship.getY()-coords.getY()));
				ship.setX(coords.getX());
				ship.setY(coords.getY());
				map.setShip(ship);
				return;
			}
		}
	}

	public void deleteShip(Ship ship){
		map.deleteShip(ship.getCoords());
		enemyShips.remove(ship);
	}

	public boolean isShipSelected(){
		for (Ship ship : playerShips){
			if (ship.getX() == selectionX && ship.getY() == selectionY && showSelection){
				return true;
			}
		}
		return false;
	}

	public Ship getSelectedShip(){
		for (Ship ship : playerShips){
			if (ship.getX() == selectionX && ship.getY() == selectionY && showSelection){
				return ship;
			}
		}
		return null;
	}

	public Set<Coords> getMoveSquares(){
		if (showSelection && isShipSelected()) {
			return map.getPossibleMoves(map.getShip(new Coords(selectionX, selectionY)));
		}
		return new HashSet<Coords>();
	}

	public void startNewTurn(){ // Reset variables such as moves/attacks left for ships
		for (Ship ship: playerShips){
			ship.setMovesLeft(ship.getMoveSpeed());
			ship.setAttacksLeft(ship.getAttacksPerTurn());
		}
	}

	public void changeTurnNum(){
		if (turn == 1){
			turn = 0;
		}
		else {
			turn += 1;
		}
		startNewTurn();
	}

	public void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-5, 0, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(5, 0, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 5, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -5, 0);
		}

		if (Gdx.input.justTouched() && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			handleSelection();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			changeTurnNum();
		}

	}

	public Coords getGridLocation(){ // Returns the coordinates of the mouse in the grid
		return new Coords (((Gdx.input.getX() + (int) (camera.position.x - cameraOffsetX))/ squareSize),
				((Gdx.graphics.getHeight() - (Gdx.input.getY() - (int) (camera.position.y - cameraOffsetY))) / squareSize));
	}

	public void drawAssets(){
		drawGrid();
		drawMap();
        drawShips();
        drawMoveDisplay();
		drawSelectionSquare();
		drawTurn();
	}

	public void drawGrid(){
        for (int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                batch.draw(gridTex, i * squareSize, j * squareSize);
            }
        }
    }

    public void drawMap(){
		for (int i = 0; i < gridWidth; i++){
			for (int j = 0; j < gridHeight; j++){
				batch.draw(map.getGrid()[i][j].getTexture(), i * squareSize + 1, j * squareSize + 1);
			}
		}
	}

    public void drawShips(){
        for (Ship ship: playerShips){
			batch.draw(ship.getTexture(), ship.getX() * squareSize + 1, ship.getY() * squareSize + 1);
		}

		for (Ship ship: enemyShips){
			batch.draw(ship.getTexture(), ship.getX() * squareSize + 1, ship.getY() * squareSize + 1);
		}
    }

    public void drawMoveDisplay(){
    	if (showSelection && isShipSelected) {
            for (Coords square : moveSquares) {
                batch.draw(moveDisplayTex, square.getX() * squareSize, square.getY() * squareSize);
            }
        }
	}

    public void drawSelectionSquare(){
        if (showSelection){
            batch.draw(selectionTex, selectionX * squareSize, selectionY * squareSize);
        }
    }

    public void drawTurn(){
		// If it is the players turn, display the player turn indicator
		if (turn == 0){
			batch.draw(pTurnTex, gridWidth * squareSize - 24,gridHeight * squareSize - 24);
		}
		// Else display enemy turn indicator
		else{
			batch.draw(eTurnTex, gridWidth * squareSize - 24,gridHeight * squareSize - 24);
		}
	}
}
