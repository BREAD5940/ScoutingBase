package base;

import javafx.scene.paint.Color;

public class CustColor{
        String hexVal;

        public CustColor(Color color){
            hexVal = String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
        }

        public CustColor(String color){
            hexVal=color;
        }

        public CustColor(int intColor){
            hexVal = String.format("#%06X", (0xFFFFFF & intColor));
            Lib.report(hexVal);
        }

        /**
         * @return the hexVal
         */
        public String getHexVal() {
            return hexVal;
        }

        /**
         * @param hexVal the hexVal to set
         */
        public void setHexVal(String hexVal) {
            this.hexVal = hexVal;
        }
    }