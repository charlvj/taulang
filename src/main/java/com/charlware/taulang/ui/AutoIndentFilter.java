package com.charlware.taulang.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class AutoIndentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String text,
                             AttributeSet attr) throws BadLocationException {
        if (text.equals("\n")) {
            text = addIndentation(fb.getDocument(), offset);
        }
        super.insertString(fb, offset, text, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
        if (text.equals("\n")) {
            text = addIndentation(fb.getDocument(), offset);
        }
        super.replace(fb, offset, length, text, attrs);
    }

    // Method to add indentation based on the previous line's indentation
    private String addIndentation(javax.swing.text.Document doc, int offset)
            throws BadLocationException {
        int start = offset - 1;
        while (start >= 0 && doc.getText(start, 1).charAt(0) != '\n') {
            start--;
        }
        start++; // Move to the actual start of the line

        int end = offset;
        while (end < doc.getLength() && doc.getText(end, 1).charAt(0) != '\n') {
            end++;
        }

        String line = doc.getText(start, end - start);

        int indentStart = 0;
        while (indentStart < line.length() && Character.isWhitespace(line.charAt(indentStart))) {
            indentStart++;
        }

        return "\n" + line.substring(0, indentStart);
    }
}
