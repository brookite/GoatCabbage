package brookite.games.goatcabbage.ui.widgets;

import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.entities.Entity;
import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.levels.GameEnvironment;
import brookite.games.goatcabbage.model.levels.LevelGameEnvironment;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.ui.WidgetFactory;
import net.miginfocom.swing.MigLayout;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class FieldPanel extends JPanel {
    private int _horizontalCellCount = 10;
    private int _verticalCellCount = 10;
    static final Color DEFAULT_COLOR = new Color(54, 38, 27);

    private EntityWidget _actor;

    private static final int MAX_HORIZONTAL_CELL_COUNT = 24;
    private static final int MAX_VERTICAL_CELL_COUNT = 12;

    int _usedSteps = 0;
    int _movedBox = 0;

    private Paddock _modelField;

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
                    add(new CellWidget(this), "wrap");
                }
                else {
                    add(new CellWidget(this));
                }
            }
        }
        int maxCellInLine = Math.max(_horizontalCellCount, _verticalCellCount);
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
        return (CellWidget) getComponent((row - 1) * _verticalCellCount + (col - 1));
    }

    public EntityWidget getActorWidget() {
        return _actor;
    }

    public FieldPanel() {
        super();
        setBackground(DEFAULT_COLOR);
    }

    public void setPaddock(Paddock paddock) {
        _modelField = paddock;
        setHorizontalCellCount(paddock.getWidth());
        setVerticalCellCount(paddock.getHeight());
        setLayout(new MigLayout("gap 1"));
        prepareNewField();
        placeWidgets();
    }

    private void placeWidgets() {
        for (Cell cell : _modelField) {
            List<Entity> entities = cell.getEntities();
            Collections.reverse(entities);
            for (Entity entity : entities) {
                EntityWidget entityWidget = WidgetFactory.placeEntityWidget(entity, this);
                if (entityWidget instanceof GoatWidget) {
                    _actor = entityWidget;
                    _usedSteps = ((Goat)_actor.getModelEntity()).getStepAmount();
                    _actor.requestFocus();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getUsedSteps() {
        return _usedSteps;
    }

    public int getMovedBox() {
        return _movedBox;
    }
}
