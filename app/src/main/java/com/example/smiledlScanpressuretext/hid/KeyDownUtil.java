package com.example.smiledlScanpressuretext.hid;

import java.util.HashMap;
import java.util.Map;

public class KeyDownUtil {

    /**
     * Key code constant: '0' key.
     */
    public static final int KEYCODE_0 = 7;
    /**
     * Key code constant: '1' key.
     */
    public static final int KEYCODE_1 = 8;
    /**
     * Key code constant: '2' key.
     */
    public static final int KEYCODE_2 = 9;
    /**
     * Key code constant: '3' key.
     */
    public static final int KEYCODE_3 = 10;
    /**
     * Key code constant: '4' key.
     */
    public static final int KEYCODE_4 = 11;
    /**
     * Key code constant: '5' key.
     */
    public static final int KEYCODE_5 = 12;
    /**
     * Key code constant: '6' key.
     */
    public static final int KEYCODE_6 = 13;
    /**
     * Key code constant: '7' key.
     */
    public static final int KEYCODE_7 = 14;
    /**
     * Key code constant: '8' key.
     */
    public static final int KEYCODE_8 = 15;
    /**
     * Key code constant: '9' key.
     */
    public static final int KEYCODE_9 = 16;
    /**
     * Key code constant: '*' key.
     */
    public static final int KEYCODE_STAR = 17;
    /**
     * Key code constant: '#' key.
     */
    public static final int KEYCODE_POUND = 18;

    /**
     * Key code constant: 'A' key.
     */
    public static final int KEYCODE_A = 29;
    /**
     * Key code constant: 'B' key.
     */
    public static final int KEYCODE_B = 30;
    /**
     * Key code constant: 'C' key.
     */
    public static final int KEYCODE_C = 31;
    /**
     * Key code constant: 'D' key.
     */
    public static final int KEYCODE_D = 32;
    /**
     * Key code constant: 'E' key.
     */
    public static final int KEYCODE_E = 33;
    /**
     * Key code constant: 'F' key.
     */
    public static final int KEYCODE_F = 34;
    /**
     * Key code constant: 'G' key.
     */
    public static final int KEYCODE_G = 35;
    /**
     * Key code constant: 'H' key.
     */
    public static final int KEYCODE_H = 36;
    /**
     * Key code constant: 'I' key.
     */
    public static final int KEYCODE_I = 37;
    /**
     * Key code constant: 'J' key.
     */
    public static final int KEYCODE_J = 38;
    /**
     * Key code constant: 'K' key.
     */
    public static final int KEYCODE_K = 39;
    /**
     * Key code constant: 'L' key.
     */
    public static final int KEYCODE_L = 40;
    /**
     * Key code constant: 'M' key.
     */
    public static final int KEYCODE_M = 41;
    /**
     * Key code constant: 'N' key.
     */
    public static final int KEYCODE_N = 42;
    /**
     * Key code constant: 'O' key.
     */
    public static final int KEYCODE_O = 43;
    /**
     * Key code constant: 'P' key.
     */
    public static final int KEYCODE_P = 44;
    /**
     * Key code constant: 'Q' key.
     */
    public static final int KEYCODE_Q = 45;
    /**
     * Key code constant: 'R' key.
     */
    public static final int KEYCODE_R = 46;
    /**
     * Key code constant: 'S' key.
     */
    public static final int KEYCODE_S = 47;
    /**
     * Key code constant: 'T' key.
     */
    public static final int KEYCODE_T = 48;
    /**
     * Key code constant: 'U' key.
     */
    public static final int KEYCODE_U = 49;
    /**
     * Key code constant: 'V' key.
     */
    public static final int KEYCODE_V = 50;
    /**
     * Key code constant: 'W' key.
     */
    public static final int KEYCODE_W = 51;
    /**
     * Key code constant: 'X' key.
     */
    public static final int KEYCODE_X = 52;
    /**
     * Key code constant: 'Y' key.
     */
    public static final int KEYCODE_Y = 53;
    /**
     * Key code constant: 'Z' key.
     */
    public static final int KEYCODE_Z = 54;
    /**
     * Key code constant: ',' key.
     */
    public static final int KEYCODE_COMMA = 55;
    /**
     * Key code constant: '.' key.
     */
    public static final int KEYCODE_PERIOD = 56;
    /**
     * Key code constant: Left Shift modifier key.
     */
    public static final int KEYCODE_SHIFT_LEFT = 59;
    /**
     * Key code constant: Right Shift modifier key.
     */
    public static final int KEYCODE_SHIFT_RIGHT = 60;
    /**
     * Key code constant: Space key.
     */
    public static final int KEYCODE_SPACE = 62;

    /**
     * Key code constant: '`' (backtick) key.
     */
    public static final int KEYCODE_GRAVE = 68;
    /**
     * Key code constant: '-'.
     */
    public static final int KEYCODE_MINUS = 69;
    /**
     * Key code constant: '=' key.
     */
    public static final int KEYCODE_EQUALS = 70;
    /**
     * Key code constant: '[' key.
     */
    public static final int KEYCODE_LEFT_BRACKET = 71;
    /**
     * Key code constant: ']' key.
     */
    public static final int KEYCODE_RIGHT_BRACKET = 72;
    /**
     * Key code constant: '\' key.
     */
    public static final int KEYCODE_BACKSLASH = 73;
    /**
     * Key code constant: ';' key.
     */
    public static final int KEYCODE_SEMICOLON = 74;
    /**
     * Key code constant: ''' (apostrophe) key.
     */
    public static final int KEYCODE_APOSTROPHE = 75;
    /**
     * Key code constant: '/' key.
     */
    public static final int KEYCODE_SLASH = 76;
    /**
     * Key code constant: '@' key.
     */
    public static final int KEYCODE_AT = 77;

    /**
     * Key code constant: '+' key.
     */
    public static final int KEYCODE_PLUS = 81;

    private static final Map<Integer, Character> DICTIONARY = new HashMap<>();

    static {
        DICTIONARY.put(KEYCODE_0, '0');
        DICTIONARY.put(KEYCODE_1, '1');
        DICTIONARY.put(KEYCODE_2, '2');
        DICTIONARY.put(KEYCODE_3, '3');
        DICTIONARY.put(KEYCODE_4, '4');
        DICTIONARY.put(KEYCODE_5, '5');
        DICTIONARY.put(KEYCODE_6, '6');
        DICTIONARY.put(KEYCODE_7, '7');
        DICTIONARY.put(KEYCODE_8, '8');
        DICTIONARY.put(KEYCODE_9, '9');
        DICTIONARY.put(KEYCODE_A, 'a');
        DICTIONARY.put(KEYCODE_B, 'b');
        DICTIONARY.put(KEYCODE_C, 'c');
        DICTIONARY.put(KEYCODE_D, 'd');
        DICTIONARY.put(KEYCODE_E, 'e');
        DICTIONARY.put(KEYCODE_F, 'f');
        DICTIONARY.put(KEYCODE_G, 'g');
        DICTIONARY.put(KEYCODE_H, 'h');
        DICTIONARY.put(KEYCODE_I, 'i');
        DICTIONARY.put(KEYCODE_J, 'j');
        DICTIONARY.put(KEYCODE_K, 'k');
        DICTIONARY.put(KEYCODE_L, 'l');
        DICTIONARY.put(KEYCODE_M, 'm');
        DICTIONARY.put(KEYCODE_N, 'n');
        DICTIONARY.put(KEYCODE_O, 'o');
        DICTIONARY.put(KEYCODE_P, 'p');
        DICTIONARY.put(KEYCODE_Q, 'q');
        DICTIONARY.put(KEYCODE_R, 'r');
        DICTIONARY.put(KEYCODE_S, 's');
        DICTIONARY.put(KEYCODE_T, 't');
        DICTIONARY.put(KEYCODE_U, 'u');
        DICTIONARY.put(KEYCODE_V, 'v');
        DICTIONARY.put(KEYCODE_W, 'w');
        DICTIONARY.put(KEYCODE_X, 'x');
        DICTIONARY.put(KEYCODE_Y, 'y');
        DICTIONARY.put(KEYCODE_Z, 'z');
        DICTIONARY.put(KEYCODE_COMMA, ',');
        DICTIONARY.put(KEYCODE_PERIOD, '.');
        DICTIONARY.put(KEYCODE_MINUS, '-');
        DICTIONARY.put(KEYCODE_EQUALS, '=');
        DICTIONARY.put(KEYCODE_LEFT_BRACKET, '[');
        DICTIONARY.put(KEYCODE_RIGHT_BRACKET, ']');
        DICTIONARY.put(KEYCODE_BACKSLASH, '\\');
        DICTIONARY.put(KEYCODE_SEMICOLON, ';');
        DICTIONARY.put(KEYCODE_SLASH, '/');
        DICTIONARY.put(KEYCODE_APOSTROPHE, '\'');
        DICTIONARY.put(KEYCODE_GRAVE, '`');
        DICTIONARY.put(KEYCODE_SPACE, ' ');
    }

    public static char getCharFromDictionary(int keycode1, int keycode2) {
        if (keycode1 == KEYCODE_SHIFT_LEFT) {
            if (keycode2 >= KEYCODE_A && keycode2 <= KEYCODE_Z) {
                return (char) (DICTIONARY.get(keycode2) - 32);
            } else {
                return leftShift(keycode2);
            }
        } else {
            return 0;
        }
    }

    public static char getCharFromDictionary(int keycode) {
        try {
            Character character = DICTIONARY.get(keycode);
            if (character == null) {
                return 0;
            } else {
                return character;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static char leftShift(int keycode) {
        switch (keycode) {
            case KEYCODE_0:
                return ')';

            case KEYCODE_1:
                return '!';

            case KEYCODE_2:
                return '@';

            case KEYCODE_3:
                return '#';

            case KEYCODE_4:
                return '$';

            case KEYCODE_5:
                return '%';

            case KEYCODE_6:
                return '^';

            case KEYCODE_7:
                return '&';

            case KEYCODE_8:
                return '*';

            case KEYCODE_9:
                return '(';

            case KEYCODE_GRAVE:
                return '~';

            case KEYCODE_MINUS:
                return '_';

            case KEYCODE_APOSTROPHE:
                return '"';

            case KEYCODE_SLASH:
                return '?';

            case KEYCODE_BACKSLASH:
                return '|';

            case KEYCODE_LEFT_BRACKET:
                return '{';

            case KEYCODE_RIGHT_BRACKET:
                return '}';

            case KEYCODE_EQUALS:
                return '+';

            case KEYCODE_SEMICOLON:
                return ':';

            case KEYCODE_PERIOD:
                return '>';

            case KEYCODE_COMMA:
                return '<';
        }
        return 0;
    }

    public static boolean isLeftShift(int keycode) {
        return keycode == KEYCODE_SHIFT_LEFT;
    }
}
