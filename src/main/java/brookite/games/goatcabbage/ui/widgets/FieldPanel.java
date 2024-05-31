package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.ui.WidgetFactory;
import net.miginfocom.swing.MigLayout;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class FieldPanel extends JPanel {
    private int horizontalCellCount = 10;
    private int verticalCellCount = 10;
    static final Color DEFAULT_COLOR = new Color(54, 38, 27);

    private EntityWidget actor;

    private static final int MAX_HORIZONTAL_CELL_COUNT = 24;
    private static final int MAX_VERTICAL_CELL_COUNT = 15;

    int usedSteps = 0;
    int movedBox = 0;

    private Paddock modelField;

    public void setHorizontalCellCount(int count) throws IllegalArgumentException {
        if (count > MAX_HORIZONTAL_CELL_COUNT) {
            throw new IllegalArgumentException(String.format(
                    "Количество ячеек по горизонтали превышает максимальное допустимое. Максимальное: %d",
                    MAX_HORIZONTAL_CELL_COUNT));
        }
        horizontalCellCount = count;
    }

    public void setVerticalCellCount(int count) {
        if (count > MAX_VERTICAL_CELL_COUNT) {
            throw new IllegalArgumentException(String.format(
                    "Количество ячеек по вертикали превышает максимальное допустимое. Максимальное: %d",
                    MAX_VERTICAL_CELL_COUNT));
        }
        verticalCellCount = count;
    }

    void prepareNewField() {
        this.removeAll();
        for (int i = 0; i < verticalCellCount; i++) {
            for (int j = 0; j < horizontalCellCount; j++) {
                if (j == horizontalCellCount - 1) {
                    add(new CellWidget(this), "wrap");
                }
                else {
                    add(new CellWidget(this));
                }
            }
        }
        int maxCellInLine = Math.max(horizontalCellCount, verticalCellCount);
        if (maxCellInLine <= 5) {
            setCellSize(CellWidget.LARGE_SIZE);
        } else if (maxCellInLine <= 8) {
            setCellSize(CellWidget.MEDIUM_SIZE);
        } else {
            setCellSize(CellWidget.SMALL_SIZE);
        }
    }

    public void setCellSize(int size) {
        for (Component cmp : getComponents()) {
            Dimension dim = new Dimension(size, size);
            cmp.setPreferredSize(dim);
            cmp.setSize(dim);
        }
    }

    public void changeBackground(ImageIcon icon) {
        CellWidget.icon = icon;
    }

    public CellWidget cellAt(CellPosition pos) {
        return cellAt(pos.row, pos.col);
    }

    public CellWidget cellAt(int row, int col) {
        return (CellWidget) getComponent((row - 1) * horizontalCellCount + (col - 1));
    }

    public EntityWidget getActorWidget() {
        return actor;
    }

    public FieldPanel(Paddock paddock) {
        super();
        setBackground(DEFAULT_COLOR);
        setPaddock(paddock);
    }

    private void setPaddock(Paddock paddock) {
        modelField = paddock;
        setHorizontalCellCount(paddock.getWidth());
        setVerticalCellCount(paddock.getHeight());
        setLayout(new MigLayout("gap 1"));
        prepareNewField();
        placeWidgets();
    }

    private void placeWidgets() {
        for (Cell cell : modelField) {
            List<Entity> entities = cell.getEntities();
            Collections.reverse(entities);
            for (Entity entity : entities) {
                EntityWidget entityWidget = WidgetFactory.placeEntityWidget(entity, this);
                if (entityWidget instanceof GoatWidget) {
                    actor = entityWidget;
                    actor.requestFocus();
                }
            }
        }
    }

    public int getUsedSteps() {
        return usedSteps;
    }

    public int getMovedBox() {
        return movedBox;
    }
}
