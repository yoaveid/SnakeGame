import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class GamePanel extends JPanel implements ActionListener{
	
	TextField text = new TextField("");
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS =(SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
	static final int NUMֹAPPELS = 1;
	static final int BONUSAPPELS = 2;
	static int DEALY = 75;
	static int DEALYBETWEEN = 10000;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	static final int startX = 56;
	static final int startY = 126;
	static int [] tackleX = new int [NUMֹAPPELS + 5];
	static int []tackleY = new int [NUMֹAPPELS + 5];
	int bodyParts =6;
	int applesEaten;
	int appleX[] = new int[NUMֹAPPELS + BONUSAPPELS];
	int appleY[] = new int[NUMֹAPPELS + BONUSAPPELS];
	char direction = 'R';
	boolean running = false;
	static boolean pause = false;
	static boolean moved = false;
	boolean hasPower = false;
	boolean powerInBoard = false;
	static int  powerX;
	static int  powerY;
	Timer timerBody,timerBetween,timerPower;
	Random random;
	JButton newGameButton;
	
	public GamePanel() {
//		for(int i = bodyParts - 1; i>= 0; i--)
//		{
//			System.out.println(i);
//			x[i] = startX - i;
//			y[i] = startY;
//
//		}
		newGameButton = new JButton("new game");
		newGameButton.addActionListener(this);
		newGameButton.setFocusable(false);
		newGameButton.setVisible(true);
		newGameButton.setForeground(Color.red);
		newGameButton.setFont(new Font("Ink Free",Font.BOLD,50));

		text.setBackground(Color.black);
		text.setPreferredSize(new Dimension());
		this.add(text);
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple(-1);
		running = true;
		timerBody = new Timer(DEALY, this);
		timerBody.start();
		timerBetween = new Timer(DEALYBETWEEN, this);
		timerBetween.start();
		timerPower = new Timer(7500 , this);

		

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		if(pause)
			drawPause(g);
		repaint();
	}
	private void drawPause(Graphics g) {
		g.setColor(Color.GRAY);
		g.setFont(new Font("Ink Fre",Font.BOLD,275));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("| |" , (SCREEN_WIDTH - metrics.stringWidth("| |")) /2, g.getFont().getSize()+50) ;
		repaint();
	}

	public void draw(Graphics g) {
		
		if(running)
		{
//			for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//
//				}
			for (int i = 0; i < appleX.length; i++) {
				if(i < NUMֹAPPELS)
					g.setColor(Color.RED);
				else
					g.setColor(Color.PINK);
				if(appleX[i] != 0 || appleY[i] !=0)
					g.fillOval(appleX[i], appleY[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			
				for(int i =bodyParts-1; i>= 0; i--)
				{
					if(i == 0) {
						g.setColor(Color.GREEN);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}
					else
					{
						if(!hasPower)
							g.setColor(new Color(45,180,0));
						else
							g.setColor(Color.magenta);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

					}
					
				}
				if(!hasPower)
					{
						g.setColor(Color.yellow);
						for (int i = 0; i < tackleX.length; i++)
							g.fillRect(tackleX[i], tackleY[i], UNIT_SIZE, UNIT_SIZE);
					}			
				g.setColor(Color.magenta);
				if(powerInBoard )
					g.fillRect(powerX, powerY, UNIT_SIZE, UNIT_SIZE);
				g.setColor(Color.BLUE);
				g.setFont(new Font("Ink Free",Font.BOLD,75));
				FontMetrics metrics = getFontMetrics(g.getFont());
				g.drawString("Score: " + applesEaten , (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) /2,g.getFont().getSize());
			}
		else
			gameOver(g);
	}

	private boolean tackle(int tackleVir, char c) {
		for (int i = 0; i <	bodyParts; i++) {
			if (c == 'x') {
				if(tackleVir == x[i])
					return false;
			}
			else
			{
				if(tackleVir == y[i])
					return false;
			}
		}
		return true;
	}

	public void move(int[] bodyX, int[] bodyY,char direction) {
		for(int i = bodyParts; i>0;i--) {
//			if(x[i] == 0 && direction == 'L')
//			{
//				x[i-1] = SCREEN_WIDTH ;
//			}
//			else if(x[i] == SCREEN_WIDTH && direction == 'R')
//			{
//				x[i-1] = 0 ;
//			}
//			else if(y[i] == 0 && direction == 'U')
//			{
//				y[i-1] = SCREEN_HEIGHT ;
//			}
//			else if(y[i] == SCREEN_HEIGHT && direction == 'D')
//			{
//				y[i-1] = 0 ;
//			}
			x[i]= x[i-1];
			y[i] = y[i-1];


		}
		moved = true;
		switch(direction) {
		
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
	}
	public void appels() {
		for (int i = 0; i < bodyParts; i++) {
			for (int j = 0; j < appleX.length; j++) {
				if((x[i] == appleX[j]) && (y[i] == appleY[j]) && (appleX[j] != 0 || appleY[j] !=0))
				{
					bodyParts++;
					applesEaten++;
					if(applesEaten% 6 == 0 && applesEaten != 0)
					{
						timerBody.stop();
						DEALY -= applesEaten/ 6;
						timerBody = new Timer(DEALY, this);
						timerBody.start();
					}
					else if(applesEaten% 8 == 0 && applesEaten != 0)
					{
						for (int k = NUMֹAPPELS; k < NUMֹAPPELS + BONUSAPPELS; k++) {
							newApple(k);
						}
					}
					if(j < NUMֹAPPELS)
						newApple(j);
					else if(j>= NUMֹAPPELS)
					{
						appleX[j] = 0;
						appleY[j] = 0;
					}
				}
			}

		}	
	}
	public void newApple(int index) {
		if(index != -1)
		{
			appleX[index] = random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;
			appleY[index] = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)* UNIT_SIZE;
			
		}
		else
		{
			for (int i = 0; i < NUMֹAPPELS ; i++) {
				appleX[i] = random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;
				appleY[i] = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)* UNIT_SIZE;
			}
			
			
		}
		for (int i = 0; i < tackleX.length; i++) {
			do {
				tackleX[i] =  random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;
				tackleY[i] =  random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;
				while (!tackle(tackleX[i],'x') ) 
					tackleX[i] =  random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;
				while (!tackle(tackleY[i],'x') ) 
					tackleY[i] =  random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;	
			} while ( tackleY[i] == appleY[0] && tackleX[i] == appleX[0]);
			
		}
		

		
	}
	public void checkCollisions() {
		
		running = isCollisions(x,y);
		if(!running) {
			timerBody.stop();
			timerBetween.stop();
			timerPower.stop();
			repaint();
		}
	}
	private boolean isCollisions(int [] x, int [] y) {
		// check if head collides with body
				for(int i = bodyParts; i> 0; i--)
				{
					if((x[0] == x[i]) && (y[0] == y[i]) && hasPower == false)
					{
						return false;
					}
				}
				//check if head touches left border
				if(x[0] < 0) {
					return false;
				}
				//check if head touches right border
				if(x[0] >= SCREEN_WIDTH) {
					return false;
				}
				//check if head touches top border
				if(y[0] < 0) {
					return false;
				}
				//check if head touches bottom border
				if(y[0] >= SCREEN_HEIGHT) {
					return false;
				}
				for (int i = 0; i < tackleX.length; i++) {
					if(x[0] == tackleX[i] && y[0] == tackleY[i] && hasPower == false)
						return false;
				}
				if(x[0] == powerX && y[0] == powerY )
				{
					powerInBoard = false;
					hasPower = true;
					timerPower.start();
				}

				return true;
	}

	public void gameOver (Graphics g)  {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game over", (SCREEN_WIDTH - metrics1.stringWidth("Game over")) /2, SCREEN_HEIGHT /2);
		g.setColor(Color.BLUE);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten , (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) /2,g.getFont().getSize());
		repaint();
		newGameButton.setBounds(115, 325, 350, 100);
		newGameButton.setVisible(true);
		hasPower = false;
		powerInBoard = false;
		timerBetween.start();
		timerPower.stop();
		timerBody.stop();
		this.add(newGameButton);
//		System.exit(0);
	}
	public void actionPerformed(ActionEvent e) {
		if(running && e.getSource() == timerBody) {
//			direction = chooseDierc(x,y,direction);
			move(x,y,direction);
			appels();
			checkCollisions();
		}
		else if( e.getSource() == timerBetween) {
			timerBetween.stop();
			powerInBoard = true;
			powerX = random.nextInt((int)SCREEN_WIDTH/ UNIT_SIZE) * UNIT_SIZE;
			powerY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)* UNIT_SIZE;
		}
		else if( e.getSource() == timerPower) {
			hasPower = false;
			timerPower.stop();
			timerBetween.start();
		}
		else if(e.getSource() == newGameButton)
		{
			startOver();
			startGame();
		}
		repaint();
		
	}
	private void startOver() {
		applesEaten = 0;
		direction = 'R';
		bodyParts = 6;
		timerBody.stop();
		timerBetween.stop();
		timerPower.stop();
		moved = false;

		DEALY = 75;
		for (int i = 0; i < x.length; i++) {
			x[i]= 0;
			y[i] =0;
				
		}
		for (int i = 0; i < appleX.length; i++) {
			appleX[i]= 0;
			appleY[i] =0;
		}
		newGameButton.setVisible(false);
		repaint();

	}
	/*
	private void choose(){
		
	}
	private char chooseDierc( int[] bodyX, int [] bodyY, char Dir) {
		if (appleX[0] == bodyX[0] && appleY[0] == bodyY[0])
			return Dir;
		System.out.println(Dir);
		char tempDir = Dir;
		switch (Dir) {
		case 'U':
			if(bodyX[0] < appleX[0])
				tempDir = 'R';
			else if(bodyY[0] > appleY[0])
				tempDir = 'L';
			break;
		case 'D':
			if(bodyX[0] < appleX[0])
				tempDir = 'R';
			else if(bodyY[0] < appleY[0])
				tempDir = 'U';
			break;
		case 'L':
			if(bodyY[0] < appleY[0])
				tempDir = 'U';
			else if(bodyX[0] > appleX[0])
				tempDir = 'D';
			break;
		case 'R':
			if(bodyY[0] < appleY[0])
				tempDir = 'U';
			else if(bodyX[0] < appleX[0])
				tempDir = 'D';
			break;
		}
		move(bodyX,bodyY,tempDir);
		for (int i = 0; i < bodyY.length; i++) {
			System.out.println(x[i]);
		}
		if(isCollisions(bodyX, bodyY))
		{
			System.out.println("fsd");
			return chooseDierc( bodyX,  bodyY, tempDir);
		}
		return tempDir;
	*/



	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: 
				if(direction != 'R' && pause == false) {
					if(!moved)
					{
						move(x, y, direction);
						appels();
						checkCollisions();

					}
					moved = false;
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT: 
				if(direction != 'L' && pause == false) {
					if(!moved)
					{
						move(x, y, direction);
						appels();
						checkCollisions();

					}
					moved = false;
					direction = 'R';
				}
				break;
				
			case KeyEvent.VK_UP: 
				if(direction != 'D'  && pause == false) {
					if(!moved)
					{
						move(x, y, direction);
						appels();
						checkCollisions();

					}
					moved = false;
					direction = 'U';
				}
				break;
			
			case KeyEvent.VK_DOWN: 
				if(direction != 'U' && pause == false) {
					if(!moved)
					{
						move(x, y, direction);
						appels();
						checkCollisions();
					}
					moved = false;
					direction = 'D';
				}
				break;
			case KeyEvent.VK_SPACE:
			{
				if(running)
				{
					if(!pause)
					{
						timerBody.stop();
						timerBetween.stop();
						timerPower.stop();
						pause = true;
					}
					else
					{
						timerBody.restart();
						timerBetween.restart();
						timerPower.restart();
						pause =false;
					}
					break;
				}
			}
			case KeyEvent.VK_ENTER: 
				if(!running)
				{
					startOver();
					startGame();
					repaint();
				}
			}
		}
		
	}
	
}

