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
import com.badlogic.gdx.utils.CharArray;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Game extends ApplicationAdapter {
	// Initialise camera and UI textures
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture gridTex, selectionTex, moveDisplayTex, pTurnTex, eTurnTex;
	private Texture oneTex, twoTex, threeTex, fourTex, fiveTex, sixTex, sevenTex, eightTex, nineTex, zeroTex;

	// Constants about the board size, and image dimensions of squares that make up the board.
	public static final int squareSize = 34; // Pixel size of a square (including the black border)
	public static final int gridWidth = 32; // Width of the grid
	public static final int gridHeight = 32; // Height of the grid

	// Selection variables
	private int selectionX = 0;
	private int selectionY = 0;
	private boolean showSelection = false;
	private boolean isShipSelected = false;
	private Set<Coords> moveSquares = new HashSet<Coords>();

	// Map and contents variables
	private Map map;
	private ArrayList<Ship> playerShips;
	private ArrayList<Ship> enemyShips;
	private College derwentCollege, jamesCollege, vanbrughCollege;
	private Department historyDept, physicsDept;

	// Game logic variables
	int turn = 0; // 0 means player turn, 1 & 2 are other colleges

	//Setup point system
	private PointSystem pointSystem = new PointSystem("player");

	// These variables are the offset of the camera to show the corner map in the bottom left of the screen
	private float cameraOffsetX;
	private float cameraOffsetY;



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


		// Load UI textures
		gridTex = new Texture("32gridSquare.png");
		selectionTex = new Texture("32selection.png");
		moveDisplayTex = new Texture("32moveDisplay.png");
		pTurnTex = new Texture("24playerTurn.png");
		eTurnTex = new Texture("24enemyTurn.png");

		// Load digit textures
		oneTex = new Texture("one.png");
		twoTex = new Texture("two.png");
		threeTex = new Texture("three.png");
		fourTex = new Texture("four.png");
		fiveTex = new Texture("five.png");
		sixTex = new Texture("six.png");
		sevenTex = new Texture("seven.png");
		eightTex = new Texture("eight.png");
		nineTex = new Texture("nine.png");
		zeroTex = new Texture("zero.png");


		// Create data structures for map and contents
		map = new Map("Maps/32x32Map.txt");
		playerShips = new ArrayList<Ship>();
		enemyShips = new ArrayList<Ship>();

		// Add some placeholder ships (for testing)
		playerShips.add(new Ship(0,0,1,"james"));
		playerShips.add(new Ship(1,0,1,"james"));
		playerShips.add(new Ship(2,0,1,"james"));
		enemyShips.add(new Ship(6,2, 1, "derwent"));
		enemyShips.add(new Ship(6,6, 1, "derwent"));
		enemyShips.add(new Ship(8,4, 1, "derwent"));

		// Add the colleges
		jamesCollege = new College("james", map.getJamesCoords(), 1, playerShips);
		derwentCollege = new College("derwent", map.getDerwentCoords(), 1, enemyShips);
		vanbrughCollege = new College("vanbrugh", map.getVanbrughCoords(), 1, new ArrayList<Ship>());

		// Add the departments
		historyDept = new Department("history", map.getHistoryCoords(), 1);
		physicsDept = new Department("history", map.getPhysicsCoords(), 1);

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

		// Draw textures
		batch.begin();
		drawAssets();
		batch.end();

        moveSquares = getMoveSquares();
        isShipSelected = isShipSelected();
        handleInput();

		camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2f, (float) squareSize*gridWidth - cameraOffsetX);
		camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2f, (float) squareSize*gridHeight - cameraOffsetY);
    }

	public void handleSelection(){
		// Get the most recent coordinates the player clicked on
		Coords coords = getGridLocation();

		// Handle cases where a ship is selected
		if (isShipSelected() && turn == 0){
			// Move the ship to a new location
			if (map.isValidSquare(coords)){
				moveSelectedShip(coords);
			}

			// If the location contains an enemy ship (to attack)
			else if (map.getShip(coords) != null){
				Ship newSelectedShip = map.getShip(coords);
				// If the new location contains an enemy ship
				if (!newSelectedShip.getTeam().equals("james")){
					// If the ship can attack
					if (getSelectedShip().canAttack()){
						// Check if the play is next to the ship (for a valid attack)
						if (getSelectedShip().nextToShip(newSelectedShip)){
							// Successfully attack the ship
							pointSystem.addPoints("ship");
							getSelectedShip().decAttacksLeft();
							deleteShip(newSelectedShip);
						}
					}

				}
			}

			// Handle cases where an enemy college is selected
			if (coords.equals(derwentCollege.getCoords())){ // If Derwent is attacked
				if (getSelectedShip().canAttack()){
					if (getSelectedShip().nextToCoords(derwentCollege.getCoords())){
						pointSystem.addPoints("college");
						map.getSquare(derwentCollege.getCoords()).setTexture("l");
					}
				}
			}

			if (coords.equals(vanbrughCollege.getCoords())){ // If Vanbrugh is attacked
				if (getSelectedShip().canAttack()){
					if (getSelectedShip().nextToCoords(vanbrughCollege.getCoords())){
						pointSystem.addPoints("college");
						map.getSquare(vanbrughCollege.getCoords()).setTexture("l");
					}
				}
			}

			// Handle cases where a department is selected
			if (coords.equals(historyDept.getCoords())){ // If History Dept is attacked
				if (getSelectedShip().canAttack()){
					if (getSelectedShip().nextToCoords(historyDept.getCoords())){
						pointSystem.addPoints("department");
						map.getSquare(historyDept.getCoords()).setTexture("l");
					}
				}
			}

			if (coords.equals(physicsDept.getCoords())){ // If Physics Dept is attacked
				if (getSelectedShip().canAttack()){
					if (getSelectedShip().nextToCoords(physicsDept.getCoords())){
						pointSystem.addPoints("department");
						map.getSquare(physicsDept.getCoords()).setTexture("l");
					}
				}
			}
			showSelection = false;
		}
		// If the player selects the same square, invert the selection (to un-select it or re-select)
		else if (coords.equals(new Coords(selectionX, selectionY))) {
			showSelection = !showSelection;
		}
		else {
			showSelection = true;
		}
		selectionX = coords.getX();
		selectionY = coords.getY();
	}

	public void moveSelectedShip(Coords coords) {
		Ship ship = getSelectedShip();
		for (Coords moveSquare : moveSquares) { // Iterate through all the squares the ship can move to
			// Check if the coordinates the ship can move to the square the player selected
			if (coords.equals(moveSquare)) {
				map.deleteShip(ship.getCoords());
				// Decrease the number of moves the ship has left based on how far it traveled
				ship.decMovesLeft(Math.abs(ship.getX()-coords.getX()) + Math.abs(ship.getY()-coords.getY()));
				// Move the ship
				ship.setX(coords.getX());
				ship.setY(coords.getY());
				// Update the ships location on the map
				map.setShip(ship);
				return;
			}
		}
	}

	public void deleteShip(Ship ship){
		map.deleteShip(ship.getCoords());
		if(ship.getTeam().equals("james")){
			playerShips.remove(ship);
		}
		else{
			enemyShips.remove(ship);
		}	}

	public boolean isShipSelected(){ // Check if the player has selected one of their ships
		for (Ship ship : playerShips){
			if (ship.getX() == selectionX && ship.getY() == selectionY && showSelection){
				return true;
			}
		}
		return false;
	}

	public Ship getSelectedShip(){ // Return the selected ship (if one is selected)
		for (Ship ship : playerShips){
			if (ship.getX() == selectionX && ship.getY() == selectionY && showSelection){
				return ship;
			}
		}
		return null;
	}

	public Set<Coords> getMoveSquares(){ // Returns a list of the squares the selected ship can move to
		if (showSelection && isShipSelected()) {
			if (getSelectedShip().getMovesLeft() > 0){
				return map.getPossibleMoves(getSelectedShip());
			}
		}
		return new HashSet<Coords>();
	}

	public void startNewTurn(){ // Reset variables such as moves/attacks left for ships
		if (turn == 0) {
			for (Ship ship : playerShips) {
				ship.setMovesLeft(ship.getMoveSpeed());
				ship.setAttacksLeft(ship.getAttacksPerTurn());
			}
		}

		else {
			for (int i = 0; i < enemyShips.size(); i++) {
				EnemyShipAI enemyShipAI = new EnemyShipAI(enemyShips.get(i), map);
				if (enemyShipAI.isNearAShip()) {
					Ship shipDestroyed = enemyShipAI.attackEnemyShip();
					deleteShip(shipDestroyed);
					continue;
				}
			}
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
			if ((Gdx.input.getX() <= gridWidth * squareSize) &&
					(Gdx.input.getY() >= Gdx.graphics.getHeight() - gridHeight * squareSize)) {
				handleSelection();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { // Press Enter to change turn
			changeTurnNum();
		}
	}

	public Coords getGridLocation(){ // Returns the coordinates of the mouse in the grid
		return new Coords (((Gdx.input.getX() + (int) (camera.position.x - cameraOffsetX))/ squareSize),
				((Gdx.graphics.getHeight() - (Gdx.input.getY() - (int) (camera.position.y - cameraOffsetY))) / squareSize));
	}

	public void drawAssets(){ // Draws all the assets
		drawGrid();
		drawMap();
        drawShips();
        drawMoveDisplay();
		drawSelectionSquare();
		drawTurn();
		drawPoints();
	}

	public void drawGrid(){ // Draws the grid to the screen
        for (int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                batch.draw(gridTex, i * squareSize, j * squareSize);
            }
        }
    }

    public void drawMap(){ // Draws the map to the screen
		for (int i = 0; i < gridWidth; i++){
			for (int j = 0; j < gridHeight; j++){
				batch.draw(map.getGrid()[i][j].getTexture(), i * squareSize + 1, j * squareSize + 1);
			}
		}
	}

    public void drawShips(){ // Draws ships to the screen
        for (Ship ship: playerShips){
			batch.draw(ship.getTexture(), ship.getX() * squareSize + 1, ship.getY() * squareSize + 1);
		}

		for (Ship ship: enemyShips){
			batch.draw(ship.getTexture(), ship.getX() * squareSize + 1, ship.getY() * squareSize + 1);
		}
    }

    public void drawMoveDisplay(){ // Draws available moves for a selected ship
    	// If the player has a ship selected, show the possible squares the ship can move to
		if (showSelection && isShipSelected && turn == 0) {
			for (Coords square : moveSquares) {
				batch.draw(moveDisplayTex, square.getX() * squareSize, square.getY() * squareSize);
			}
		}
	}

    public void drawSelectionSquare(){ // Draws the selection square to the screen
        if (showSelection){
            batch.draw(selectionTex, selectionX * squareSize, selectionY * squareSize);
        }
    }

    public void drawTurn(){
		// If it is the players turn, display the player turn indicator
		if (turn == 0){
			batch.draw(pTurnTex, camera.position.x - cameraOffsetY,
					camera.position.y - cameraOffsetY + Gdx.graphics.getHeight() - 24);
		}
		// Else display enemy turn indicator
		else{
			batch.draw(eTurnTex, camera.position.x - cameraOffsetY,
					camera.position.y - cameraOffsetY + Gdx.graphics.getHeight() - 24);
		}
	}

	public void drawPoints() { // Draws the players points to the screen
		int currentDigit = 1; // Track current digit to print
		char[] points = String.valueOf(pointSystem.getPoints()).toCharArray(); // Convert to char array to read from
		for (int i = points.length-1; i >= 0 ; i--) { // Print values in reverse order
			if (points[i] == "0".charAt(0)) {
				batch.draw(zeroTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "1".charAt(0)) {
				batch.draw(oneTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "2".charAt(0)) {
				batch.draw(twoTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "3".charAt(0)) {
				batch.draw(threeTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "4".charAt(0)) {
				batch.draw(fourTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "5".charAt(0)) {
				batch.draw(fiveTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "6".charAt(0)) {
				batch.draw(sixTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "7".charAt(0)) {
				batch.draw(sevenTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit
						, Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "8".charAt(0)) {
				batch.draw(eightTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}

			else if (points[i] == "9".charAt(0)) {
				batch.draw(nineTex, Gdx.graphics.getWidth() + camera.position.x - cameraOffsetY - 14 * currentDigit,
						Gdx.graphics.getHeight() + camera.position.y - cameraOffsetY - 23);
			}
			currentDigit += 1;
		}
	}
}
