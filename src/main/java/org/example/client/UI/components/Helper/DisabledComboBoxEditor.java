
package org.example.client.UI.components.Helper;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class DisabledComboBoxEditor implements ComboBoxEditor {
    private ComboBoxEditor editor;

    public DisabledComboBoxEditor(ComboBoxEditor editor) {
        this.editor = editor;
    }

    @Override
    public Component getEditorComponent() {
        return editor.getEditorComponent();
    }

    @Override
    public void setItem(Object anObject) {
        editor.setItem(anObject);
    }

    @Override
    public Object getItem() {
        return editor.getItem();
    }

    @Override
    public void selectAll() {
        editor.selectAll();
    }

    @Override
    public void addActionListener(ActionListener l) {
        editor.addActionListener(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        editor.removeActionListener(l);
    }

    // Không ghi đè các phương thức không tồn tại trong ComboBoxEditor
}
