package org.itair;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class MyTile extends View {

	private int size = 12;

	private int xcount;
	private int ycount;

	private int xoffset;
	private int yoffset;

	private int[][] map;

	private Bitmap[] pics;

	public static final int GERRN_STAR = 1;
	public static final int RED_STAR = 2;
	public static final int YELLOW_STAR = 3;
	
	public int mm = 5;

	public MyTile(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private MyHandler handler = new MyHandler();

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			update();
			MyTile.this.invalidate();
		}
		
		public void sleep(int delay){
			
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), 600);
			
			
			
		}

	}
	
	public void clearTile(){
		
		for (int x = 0; x < xcount; x++) {
			
			for (int y = 0; y < ycount; y++) {
				setTile(0, x, y);
			}
		}
	}
	
	
	public void update(){
		//clearTile();
		buildWall();
		System.out.println("~~~~~~~update~~~~~~~~~~~");
		updateSnake();
		handler.sleep(600);
		
	}
	
	public void updateSnake(){
		mm++;
		System.out.println("-------->"+mm);
		setTile(RED_STAR, 5, mm);
	}
	
	

	public void loadPic(int key, Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, size, size);
		drawable.draw(canvas);
		
		pics[key] = bitmap;
	}

	public void setTile(int picIndex, int x, int y) {

		map[x][y] = picIndex;

	}

	public void initGame() {

		Resources r = getContext().getResources();

		pics = new Bitmap[4];

		loadPic(GERRN_STAR, r.getDrawable(R.drawable.greenstar));
		loadPic(RED_STAR, r.getDrawable(R.drawable.redstar));
		loadPic(YELLOW_STAR, r.getDrawable(R.drawable.yellowstar));

		
		update();

	}

	public void buildWall() {

		for (int x = 0; x < xcount; x++) {
			setTile(GERRN_STAR, x, 0);
			setTile(GERRN_STAR, x, ycount - 1);
		}

		for (int y = 0; y < ycount; y++) {
			setTile(GERRN_STAR, 0, y);
			setTile(GERRN_STAR, xcount - 1, y);
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		xcount = (int) Math.floor(w / size);
		ycount = (int) Math.floor(h / size);

		xoffset = (w - size * xcount) / 2;
		yoffset = (h - size * ycount) / 2;

		map = new int[xcount][ycount];
		initGame();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		System.out.println("-------onDraw---------");
		
		Paint paint = new Paint();
		/*
		 * paint.setColor(Color.RED);
		 * 
		 * canvas.drawLine(0, 0, 50, 50, paint);
		 * 
		 * canvas.drawBitmap(pics[RED_STAR], 80, 120, paint);
		 */

		for (int x = 0; x < xcount; x++) {

			for (int y = 0; y < ycount; y++) {

				if (map[x][y] > 0) {
					canvas.drawBitmap(pics[map[x][y]], xoffset + x * size,
							yoffset + y * size, paint);
				}

			}

		}
	}

}
