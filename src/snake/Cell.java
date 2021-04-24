package snake;

import java.awt.geom.Ellipse2D;


 enum CellType{
    NONE, SNAKE, APPLE}

class Cell {
    private CellType cellType;
    private Ellipse2D.Double circle = new Ellipse2D.Double();
    
    /**
	 * Default Constructor for Cell.
	 * @param name for name of animal.
	 */    
    public Cell() {this.cellType = CellType.NONE;}
    
   
    /**
	 * Non-Constructor for Cell that specifies what type of component is in a cell.
	 * @param int type what is in a cell.
	 */
    public Cell(int type){
        
        setCellType(type);
    }
    
    /**
	 * setCellType that sets what type of component is in a cell.
	 * @param int type what is in a cell.(0 = none, 1 = snake, 2 = apple)
	 */
    public void setCellType(int i){
        switch (i) {
            case 0:
                this.cellType = CellType.NONE;
                break;
            case 1:
                this.cellType = CellType.SNAKE;
                break;
            case 2:
                this.cellType = CellType.APPLE;
                break;
        }
    }
    
    /**
	 * getCellType retrieve what type of component is in a cell. (0 = none, 1 = snake, 2 = apple)
	 */
    public int getCellType(){
        switch(cellType){
            default: //if NONE
                return 0; 
            case SNAKE: 
                return 1;
            case APPLE: 
                return 2;
        }
    }
    
    /**
	 * isSnake to retrieve if the cell has a snake component. 
	 */
    public boolean isSnake(){
        return cellType.equals(CellType.SNAKE);
    }
    
    /**
	 * isApple to retrieve if the cell has an apple component. 
	 */
    public boolean isApple(){
        return cellType.equals(CellType.APPLE);
    }
    
    /**
	 * isNone to retrieve if the cell has nothing. 
	 */
    public boolean isNone(){
        return cellType.equals(CellType.NONE);
    }
    
    /**
	 * setCircle to set each grid cell to a circle. 
	 */
    public void setCircle(double x, double y, double w, double h){
        circle.setFrame(x, y, w, h);
    }
    /**
	 * getCircle to get each grid circle cell. 
	 */
    public Ellipse2D getCircle(){return circle;}

}
