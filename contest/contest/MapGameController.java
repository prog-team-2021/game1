import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;
//comment
public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public Label score;
    public Label time;
    public Label stage;
    public ImageView[] mapImageViews;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        drawMap(chara, mapData);
    }

    //ステータス
 

    // Draw the map
    public void drawMap(MoveChara c, MapData m){
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==cx && y==cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }

    }

    //
    
    //得点表示と制限時間表示とステージ数表示
    Font statusFont = new Font(Font.MONOSPACED, Font.CENTER_BASELINE, 10);

    public void paintComponent(Graphics g) {
	//super.paintComponent(g);
	g.setColor(Color.black);
		Font font = new Font(Font.SERIF, Font.BOLD, 10);
		g.setFont(font);
		g.drawString("score", 10, 10);
        g.drawString("time", 10, 20);
        g.drawString("stage", 10, 30);
	}
    
    // Get users key actions
    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode(); System.out.println("keycode:"+key);
        if (key == KeyCode.A){
        	leftButtonAction();
        }else if (key == KeyCode.S){
            downButtonAction(); 
        }else if (key == KeyCode.W){
            upButtonAction();
        }else if (key == KeyCode.D){
            rightButtonAction();
        }
    }

    // Operations for going the cat down
    public void upButtonAction(){
        printAction("UP");
        chara.setCharaDirection(MoveChara.TYPE_UP);
        chara.move(0, -1);
        drawMap(chara, mapData);
    }

    // Operations for going the cat down
    public void downButtonAction(){
        printAction("DOWN");
        chara.setCharaDirection(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void leftButtonAction(){
        printAction("LEFT");
        chara.setCharaDirection(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void rightButtonAction(){
        printAction("RIGHT");
        chara.setCharaDirection(MoveChara.TYPE_RIGHT);
        chara.move(1, 0);
        drawMap(chara, mapData);
    }

    public void func1ButtonAction(ActionEvent event) {
        System.out.println("func1: Nothing to do");
    }

    // Print actions of user inputs
    public void printAction(String actionString) {
        System.out.println("Action: " + actionString);
    }

    
    //制限時間
    public class Countdown {
        public void main(String[] args) {
    
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
            final Runnable runnable = new Runnable() {
    
                public void run() {
                    int counttime=120;
                    counttime--;
    
                    if (counttime < 0) {
                        System.out.println("Timer Over!");
                        scheduler.shutdown();
                    }
                }
            };
            scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
        }
    }

}