package org.client;

import java.awt.Color;
import java.awt.Graphics;

public class PieceDrawer {
	
	public static void drawTicTacToePiece(Graphics g, boolean isCurrentPlayer, int xLocation, int yLocation, int cellLength) {
		g.setColor(Color.BLACK);
		if (isCurrentPlayer) {
			g.drawLine(xLocation, yLocation, xLocation + cellLength, yLocation + cellLength);
			g.drawLine(xLocation + cellLength, yLocation, xLocation, yLocation + cellLength);
		} else {
			g.drawOval(xLocation, yLocation, cellLength, cellLength);
		}
	}

	public static void drawOthelloPiece(Graphics g, boolean isCurrentPlayer, int xLocation, int yLocation, int cellLength) {
		g.setColor(Color.BLACK);
		if (isCurrentPlayer) {
			g.fillOval(xLocation, yLocation, cellLength, cellLength);
		} else {
			g.drawOval(xLocation, yLocation, cellLength, cellLength);
		}
	}
	
	public static void drawCheckersPiece(Graphics g, boolean isCurrentPlayer, int xLocation, int yLocation, int cellLength) {
		g.setColor(Color.BLACK);
		if (isCurrentPlayer) {
			g.fillOval(xLocation, yLocation, cellLength, cellLength);
		} else {
			g.drawOval(xLocation, yLocation, cellLength, cellLength);
		}
		
		// casting from Cell to CheckersCell doesn't work, so we can't render if a piece is king or not for now
		// maybe if we have more time
//		if (isKing) {
//			g.setColor(Color.RED);
//			int closeEdge = (int)(cellLength * 0.3); 
//			int farEdge = (int)(cellLength * 0.7);
//			int midPoint = (int)(cellLength * 0.5);
//			
//			// draw the 'K'
//			g.drawLine(xLocation + closeEdge, yLocation + closeEdge, xLocation + closeEdge, yLocation + farEdge); // vertical stroke
//			g.drawLine(xLocation + closeEdge, yLocation + midPoint, xLocation + farEdge, yLocation + closeEdge); // upwards diagonal stroke
//			g.drawLine(xLocation + closeEdge, yLocation + midPoint, xLocation + farEdge, yLocation + farEdge); // downwards diagonal stroke
//			
//		}
		
		g.setColor(Color.BLACK); // reset color just in case
	}
}
