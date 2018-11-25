package com.limewire.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture grid, greenSquare, shipImg, selectedShipImg;
	int gridWidth = 32;
	int gridHeight = 32;
	int gridLines = 3;
	public String[][] activeGrid;
	Ship ship;
	Ship selectedShip;
	int[] selectedSquare;
	String typeSquareSelected = "false";
	String mode = "select";
	int[] mouseCoordinates;


	@Override
	public void create () {
		batch = new SpriteBatch();
		grid = new Texture("32Grid.png");
		greenSquare = new Texture("32greenSquare.png");
		shipImg = new Texture("bigship-32.png");
		selectedShipImg = new Texture("selected ship.png");
		activeGrid = new String[32][32];

		ship = new Ship(1, 0, 8);
		activeGrid[ship.getLocation()[0]][ship.getLocation()[1]] = "ship";
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            batch.begin();

            //batch.draw(greenSquare, 3, 3);
            for (int i = 0; i < gridHeight; i++){
			for (int j = 0; j < gridWidth; j++){

				if (typeSquareSelected != "false") {
					if (j == selectedSquare[1] && i == selectedSquare[0]) {
						batch.draw(greenSquare, gridLines + i * (gridWidth + gridLines),
								gridLines + j * (gridHeight + gridLines));
					}

					if (typeSquareSelected) {
						selectedShip = ship; //---- figure out how to find the selected ship thing
						mode = "action";
					}
				}


				if (activeGrid[j][i]){
					batch.draw(shipImg, gridLines + i * (gridWidth + gridLines),
							gridLines + j * (gridHeight + gridLines));
				}
				/*(if (activeGrid[j][i] == "selectedShip") {
					batch.draw(selectedShipImg, gridLines + i * (gridWidth + gridLines),
							gridLines + j * (gridHeight + gridLines));
				}*/

			}
		}

		if (mode == "select") {

			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				mouseCoordinates = getMouseLocation();
				typeSquareSelected = "true";
				selectedSquare = mouseCoordinates;
			/*if (activeGrid[coordinates[1]][coordinates[0]] == "ship") {

				activeGrid[coordinates[1]][coordinates[0]] = "selectedShip";
			}*/
			}

			if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
				typeSquareSelected = "false";
			}
		}
		else if (mode == "action"){
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				mouseCoordinates = getMouseLocation();
				ship.setLocation(mouseCoordinates);

			}
		}
		batch.draw(grid, 0, 0);
		batch.end();
	}

	public int[] getMouseLocation(){ // Returns the coordinates of the mouse in the grid
		return new int[] {(Gdx.input.getX() / (gridWidth + gridLines)),
				((Gdx.graphics.getHeight() - Gdx.input.getY()) / (gridHeight + gridLines))};
	}

	public void setGridSquare(int[] coords) {
		activeGrid[coords[0]][coords[1]] = "ship";

	}
}
