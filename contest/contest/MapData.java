import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;


public class MapData {
   public static final int TYPE_SPACE = 0;
   public static final int TYPE_WALL = 1;
   public static final int TYPE_GATE = 2;
   public static final int TYPE_SWITCH = 3;
   public static final int TYPE_TURN = 4;
   public static final int TYPE_KEY = 5;
   private static final int NUM = 6;

   //Count
   public MapGameController controller;
   public int getKeyNum(){
       return controller.KeyNum;
   }
   public int getCoinNum(){
       return controller.CoinNum;
   }
   //
    private static final String mapImageFiles[] = {
        "png/SPACE.png",
        "png/WALL.png",
        "png/gate.png",
        "png/switch.png",
        "png/turn.png",
        "png/key.png"
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width;
    private int height;

    //random
    Random rnd = new Random();
    int x_rnd;
    int y_rnd;
    boolean isExistKey;
    boolean isExistSwitch;
    //

    MapData(int x, int y){
       mapImages = new Image[NUM];
       mapImageViews = new ImageView[y][x];
       for (int i=0; i<NUM; i++) {
           mapImages[i] = new Image(mapImageFiles[i]);
       }

       width = x;
       height = y;
       maps = new int[y][x];

       fillMap(MapData.TYPE_WALL);
       digMap(1, 3);
       putturn();
       putswitch();
       putgate();
       putkey();
       setImageViews();
   }

   public int getHeight(){
       return height;
   }

   public int getWidth(){
       return width;
   }

   public int getMap(int x, int y) {
       if (x < 0 || width <= x || y < 0 || height <= y) {
           return -1;
       }
       return maps[y][x];
   }

   public ImageView getImageView(int x, int y) {
       return mapImageViews[y][x];
   }

   public void setMap(int x, int y, int type){
       if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
           return;
       }
       maps[y][x] = type;
   }

 // set images based on two-dimentional arrays (maps[y][x])
   public void setImageViews() {
       for (int y=0; y<height; y++) {
           for (int x=0; x<width; x++) {
               mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
           }
       }
   }

 // fill two-dimentional arrays with a given number (maps[y][x])
   public void fillMap(int type){
       for (int y=0; y<height; y++){
           for (int x=0; x<width; x++){
               maps[y][x] = type;
           }
       }
   }

 // dig walls for creating trails
   public void digMap(int x, int y){
       setMap(x, y, MapData.TYPE_SPACE);
       int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
       int[] tmp;

       for (int i=0; i<dl.length; i++) {
           int r = (int)(Math.random()*dl.length);
           tmp = dl[i];
           dl[i] = dl[r];
           dl[r] = tmp;
       }

       for (int i=0; i<dl.length; i++){
           int dx = dl[i][0];
           int dy = dl[i][1];
           if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
               setMap(x+dx, y+dy, MapData.TYPE_SPACE);
               digMap(x+dx*2, y+dy*2);
           }
       }
   }
   //アイテム画像との変換
   public void putgate(){
       do{
           x_rnd = rnd.nextInt(21);
           y_rnd = rnd.nextInt(15);
           if(getMap(x_rnd,y_rnd) == MapData.TYPE_SPACE){
               setMap(x_rnd,y_rnd,MapData.TYPE_GATE);
               break;
           }
           }while(getMap(x_rnd,y_rnd) != MapData.TYPE_SPACE);
   }
   public void putswitch(){
       do{
           x_rnd = rnd.nextInt(21);
           y_rnd = rnd.nextInt(15);
           if(getMap(x_rnd,y_rnd) == MapData.TYPE_SPACE){
               setMap(x_rnd,y_rnd,MapData.TYPE_SWITCH);
               break;
               }
       }while(getMap(x_rnd,y_rnd) != MapData.TYPE_SPACE);
   }
   public void putturn(){
       do{
           x_rnd = rnd.nextInt(21);
           y_rnd = rnd.nextInt(15);
           if(getMap(x_rnd,y_rnd) == MapData.TYPE_SPACE){
               setMap(x_rnd,y_rnd,MapData.TYPE_TURN);
               break;
           }
       }while(getMap(x_rnd,y_rnd) != MapData.TYPE_SPACE);
   }
   public void putkey(){
       do{
           x_rnd = rnd.nextInt(21);
           y_rnd = rnd.nextInt(15);
           if(getMap(x_rnd,y_rnd) == MapData.TYPE_SPACE){
               setMap(x_rnd,y_rnd,MapData.TYPE_KEY);
               break;
           }
       }while(getMap(x_rnd,y_rnd) != MapData.TYPE_SPACE);
   }
   public boolean getIsExistKey(){
       return isExistKey;
   }
   public void getKey(int x, int y, int type){
       setMap(x, y, type);
       isExistKey = false;
   }
   public void getSwitch(int x, int y, int type){
       setMap(x, y, type);
       isExistSwitch = false;
   }
}

// branch test