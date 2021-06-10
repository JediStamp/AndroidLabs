package com.example.androidlabs;

public class Message {
        String msgText;
        String msgType;
        long msgID;

        public Message(String msgText, String msgType, long msgID) {
            this.msgText = msgText;
            this.msgType = msgType;
            this.msgID = msgID;
        }

        public int getLayout() {
            int layout;
            if (msgType.equals("Send")) {
                layout = R.layout.send_row_layout;
            } else if (msgType.equals("Rx")) {
                layout = R.layout.rx_row_layout;
            } else {
                layout = 0;
            }
            return layout;
        }

        public int getTextId() {
            int textID;
            if (msgType.equals("Send")) {
                textID = R.id.sendTextGoesHere;
            } else if (msgType.equals("Rx")) {
                textID = R.id.rxTextGoesHere;
            } else {
                textID = 0;
            }
            return textID;
        }
}
