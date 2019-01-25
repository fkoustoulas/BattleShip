
package game;


import java.awt.Color;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;



public class BattleShip extends JFrame implements Runnable{
    int x,y, mycounter=0, enemycounter=0;
    Image dbImage, object, boom;
    Image ship, bullet1,back;
    Graphics dbGraphics;
    int xDir, yDir, bx, by,ebx, eby, objXDir=1,objx;
    Rectangle bullet, obj, enemyBullet, myObj;
    boolean readyToUse, shot = false, bReadyToUse, bShot = false, bscore = true, bescore = true;
    
    @Override
    public void run(){
        try{
            while(true){
                
                move();
                shoot();
                Thread.sleep(5);
            }
        }
        catch(Exception e){
            
        }
    }
    public void shoot(){
        if(shot)
            bullet.y-=4;
        if(enemyBullet.y>getHeight()){
                    enemyBullet = new Rectangle(0,0,0,0);
                    bShot = false;
                    bReadyToUse=true;
                    myEnemyBullet();
                    bescore = true;
                }
        if(bShot)
            enemyBullet.y += 4;
        
    }
    public void move(){
        x += xDir;
        objx += objXDir;
        if(objx>600)
            setObjDir(-1);
        if(objx<50)
            setObjDir(+1);
        if(x<=0)
            x=0;
        if(x>getWidth()-170)
            x=getWidth()-170;
    }
    public void myEnemyBullet(){
        if(enemyBullet == null)
            bReadyToUse = true;
        else if(enemyBullet.y<-10)
            bReadyToUse = true;
        if(bReadyToUse){
            ebx = objx;
            eby = 150;
            enemyBullet = new Rectangle(ebx, eby, 15, 25);
            bShot = true;
            bReadyToUse=false;
        }
    }
    public void setXDir(int x){
        xDir = x;
    }
    public void setObjDir(int x){
        objXDir = x;
    }
    public class AL extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            
            if(key == e.VK_LEFT){
                setXDir(-5);
            }
            if(key == e.VK_RIGHT){
                setXDir(+5);
            }
            if(key == e.VK_SPACE){
                if(bullet == null)
                    readyToUse = true;
                else if(bullet.y<-10){
                    readyToUse = true;
                    bscore = true;
                }
                if(readyToUse){
                    bx = x+72;
                    by = y;
                    bullet = new Rectangle(bx, by, 15, 25);
                    shot = true;
                    readyToUse=false;
                    
                }
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();
            
            if(key == e.VK_LEFT){
                setXDir(0);
            }
            if(key == e.VK_RIGHT){
                setXDir(0);
            }
            if(key == e.VK_SPACE){
                
                if(bullet.y<-10){
                    bullet = new Rectangle(0,0,0,0);
                    shot = false;
                    readyToUse=true;
                }
                
            }
        }
    }
    
    
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbGraphics = dbImage.getGraphics();
        paintComponent(dbGraphics);
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paintComponent(Graphics g) { 
        g.drawImage(back, getWidth()-800, 20, this);
        g.setColor(Color.red);
        g.drawImage(ship, x, y, this);
        
        
        if(shot){
            
            g.setColor(Color.BLACK);
            
            g.drawImage(bullet1, bullet.x-12, bullet.y-5, this);
        }
        
        if(bShot){
            
            g.setColor(Color.BLACK);
            
            g.drawImage(boom, enemyBullet.x-12, enemyBullet.y-5, this);
        }
        
        obj = new Rectangle(objx,50,210,100);
        g.setColor(Color.red);
        
        g.drawImage(object, obj.x, obj.y, this);
        if(bullet!=null && obj!=null)
            if(bullet.intersects(obj)){
                g.drawImage(boom, obj.x, obj.y, this);
                if(bscore){
                    mycounter++;
                    bscore = false;
                }
                
            }
        myObj = new Rectangle(x,450,170,100);
        
        if(enemyBullet!=null && myObj!=null)
            if(enemyBullet.intersects(myObj)){
                g.drawImage(boom, myObj.x, myObj.y, this);
                if(bescore){
                    enemycounter++;
                    bescore = false;
                }
                
            }
        g.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD + Font.ITALIC, 20)); 
        g.drawString("My score = " + mycounter, 10, 40);
        g.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD + Font.ITALIC, 20)); 
        g.drawString("Enemy score = " + enemycounter, getWidth()-200, 40);
        
        
        repaint();

    }
    
    public BattleShip(){
        //Load Images
        ImageIcon i = new ImageIcon("src/game/battleShip2.png");
        ship = i.getImage();
        i = new ImageIcon("src/game/bullet.png");
        bullet1 = i.getImage();
        i =new ImageIcon("src/game/back.png");
        back = i.getImage();
        i = new ImageIcon("src/game/obj1.png");
        object = i.getImage();
        i = new ImageIcon("src/game/boom.png");
        boom = i.getImage();
        
        //Game Parameters
        addKeyListener(new AL());
        setTitle("BattleShip");
        setSize(800,600);
        setVisible(true);
        setResizable(false);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myEnemyBullet();
        
        x = 150;
        y = 450;
    }

    public static void main(String[] args) {
        BattleShip bs = new BattleShip();
        Thread t1 = new Thread(bs);
        t1.start();
    }
    
}
