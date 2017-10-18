import processing.core.PApplet;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class LineCA {
    int cells[];
    private int ruleset[];
    int line;
    private int[] newCells;

    public LineCA(int width, int[] ruleset, Predicate<Integer> predicate) {
        this.ruleset = ruleset;
        cells = new int[width];
        newCells = new int[cells.length];
        IntStream.range(0, width)
                .filter(predicate::test)
                .forEach(i -> cells[i] = 1);
    }

    public void generate() {
        IntStream.range(1, cells.length - 1).forEach(i -> {
            int left = cells[i - 1];
            int me = cells[i];
            int right = cells[i + 1];
            newCells[i] = rules(left, me, right);
        });
        System.arraycopy(newCells, 1, cells, 1, cells.length - 1);
    }

    public void drawCell(PApplet app) {
        IntStream.range(0, app.width)
                .filter(i -> cells[i] == 1)
                .forEach(i -> app.rect(i, line, 1, 1));
        line++;
    }

    private int rules(int left, int me, int right) {
        String s = "" + left + me + right;
        int index = Integer.parseInt(s, 2);
        return ruleset[index];
    }


}
