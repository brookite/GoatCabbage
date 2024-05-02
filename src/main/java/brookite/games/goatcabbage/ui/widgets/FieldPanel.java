package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.utils.CellPosition;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel {
    private int _horizontalCellCount = 10;
    private int _verticalCellCount = 10;

    private static final int MAX_HORIZONTAL_CELL_COUNT = 24;
    private static final int MAX_VERTICAL_CELL_COUNT = 10;

    public void setHorizontalCellCount(int count) throws IllegalArgumentException {
        if (count > MAX_HORIZONTAL_CELL_COUNT) {
            throw new IllegalArgumentException(String.format(
                    "Количество ячеек по горизонтали превышает максимальное допустимое. Максимальное: %d",
                    MAX_HORIZONTAL_CELL_COUNT));
        }
        _horizontalCellCount = count;
    }

    public void setVerticalCellCount(int count) {
        if (count > MAX_VERTICAL_CELL_COUNT) {
            throw new IllegalArgumentException(String.format(
                    "Количество ячеек по вертикали превышает максимальное допустимое. Максимальное: %d",
                    MAX_VERTICAL_CELL_COUNT));
        }
        _verticalCellCount = count;
    }

    void prepareNewField() {
        this.removeAll();
        for (int i = 0; i < _verticalCellCount; i++) {
            for (int j = 0; j < _horizontalCellCount; j++) {
                if (j == _horizontalCellCount - 1) {
                    add(new CellWidget(), "wrap");
                }
                else {
                    add(new CellWidget());
                }
            }
        }
    }

    void changeBackground(ImageIcon icon) {
        CellWidget.icon = icon;
    }

    public CellWidget cellAt(CellPosition pos) {
        return cellAt(pos.row, pos.col);
    }

    public CellWidget cellAt(int row, int col) {
        return (CellWidget) getComponent((row - 1) * _verticalCellCount + (col - 1));
    }

    public FieldPanel() {
        super();
        setLayout(new MigLayout("gap 1"));
        prepareNewField();
        cellAt(2, 2).addItem(new GoatWidget(cellAt(2, 2), new Goat()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
