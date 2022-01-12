import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//a
public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_OTHERS = 2;
    private static final String mapImageFiles[] = {
        "png/SPACE.png",
        "png/WALL.png"
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width;
    private int height;

    MapData(int x, int y){
        mapImages = new Image[2];
        mapImageViews = new ImageView[y][x];
        for (int i=0; i<2; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
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
    // dlのシャッフル
        for (int i=0; i<dl.length; i++) {   //dl.length = 4
            int r = (int)(Math.random()*dl.length);  //range 0~3
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];             //dx*2,dy*2 range -2~2
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_SPACE);
                digMap(x+dx*2, y+dy*2);

            }                             //一度止まるまで線を引く。その後先端から広がっていき根元まで処理が戻ってくる。 
        }
    }

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
}
// branch test