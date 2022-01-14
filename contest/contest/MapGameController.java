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
    public ImageView[] mapImageViews;
    //status
    public Label SCORECOUNT;
    public Label STAGECOUNT;
    public Label TIMECOUNT;
    public Label ITEMDATA;
    public Label CATHP;
    public Label DIZZY;
    //status
    private int ScoreNum=0;
    private int StageNum=0;
    private int TimeNum=60;
    public  int KeyNum=0;
    public  int CoinNum=0;
    private int HpNum=100;
    private int DizzyNum=0;
    //
    //ゴール判定
    int NONE = 0;
    int GOAL = 1;
    //

    //getstatus
    public int getScoreNum(){
        return ScoreNum;
    }

    public int getStageNum(){
        return StageNum;
    }

    public int getTimeNum(){
        return TimeNum;
    }

    public int getKeyNum(){
        return KeyNum;
    }

    public int getCoinNum(){
        return CoinNum;
    }

    public int getHpNum(){
        return HpNum;
    }

    public int getDizzyNum(){
        return DizzyNum;
    }
    //

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

    //ステータス表示
    public void setNumberLabel(){
      SCORECOUNT.setText("" + getScoreNum());
      STAGECOUNT.setText("" + getStageNum());
      TIMECOUNT.setText("" + getTimeNum() + "s");
      ITEMDATA.setText("key" + getKeyNum() + " " + "coin" + getCoinNum());
      CATHP.setText("" + getHpNum());
      DIZZY.setText("" + getDizzyNum());
  }
  //

   //制限時間の実装//
   //public class Countdown {

        //final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        //final Runnable runnable = new Runnable() {
          //  public void run() {
                //TimeNum--;

                ///if (TimrNum < 0) {
                    //System.out.println("Timer Over!");
                    //scheduler.shutdown();
                //}
          //  }
        //};
        //scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);

//}
   //

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
        setNumberLabel();
    }
    //

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
   //アイテム取得とゴール確認
   public int moveCheck(){
       int cx = chara.getPosX();
       int cy = chara.getPosY();
       int cm = mapData.getMap(cx, cy);
       if (cm == MapData.TYPE_KEY) {
           System.out.println("GET KEY");
           mapData.getKey(cx, cy, MapData.TYPE_SPACE);
       } else if (cm == MapData.TYPE_SWITCH) {
           System.out.println("GET SWITCH");
           mapData.getKey(cx, cy, MapData.TYPE_SPACE);
       } else if (cm == MapData.TYPE_GATE && ! mapData.getIsExistKey()){
           return GOAL;
       }
       return NONE;
   }
}
