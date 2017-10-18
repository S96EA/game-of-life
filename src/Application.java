import processing.core.PApplet;
/**
 * Created by Neil on 2017/9/21 8:23.
 */
public class Application extends PApplet {
    TDCA ca;

    public static void main(String[] args) {
        PApplet.main("Application");
    }

    @Override
    public void settings() {
        size(1200, 900);
    }

    @Override
    public void setup() {
        background(255);
        int size = 1;
        ca = new TDCA(width / size, height / size, size, (x, y) -> (random(0, 2) < 0.8));
        ca.draw(this);

    }

    @Override
    public void draw() {
        background(255);
        drawBoundary();
        ca.generate();
        ca.draw(this);
    }

    void drawBoundary() {
        fill(100);
        rect(0, 0, 1, height);
        rect(0, 0, width, 1);
        rect(width - 1, 0, 1, height);
        rect(0, height - 1, width, 1);
    }

    @Override
    public void mousePressed() {
        background(255);
        drawBoundary();
        ca.generate();
        ca.draw(this);
    }

    @Override
    public void keyPressed() {
        if (key == 'z') {
            ca.updateCell(mouseX, mouseY);
            ca.draw(this);
        }
        if (key == 'x') {
            background(255);
            drawBoundary();
            ca.generate();
            ca.draw(this);
        }
        if (key == 'c') {
            background(255);
            drawBoundary();
            ca.clean();
            ca.draw(this);
        }
    }
}

