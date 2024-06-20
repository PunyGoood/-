package com.example.test1.ocr.bean;

import java.util.List;

public class ReturnData {

/*
* {
    "error_code":"0",
    "error_msg":"success",
    "data":{
        "from":"zh",
        "to":"en",
        "content":[
            {
                "src":"这是一个测试 ",
                "dst":"This is a test.",
                "rect":"79 23 246 43",
                "lineCount":1,
                "pasteImg":"xxx",
                "points":[
                    {
                        "x":0,
                        "y":0
                    },
                    {
                        "x":0,
                        "y":0
                    },
                    {
                        "x":20,
                        "y":20
                    },
                    {
                        "x":10,
                        "y":10
                    }
                ]
            },
            {
                "src":"这是一个例子 ",
                "dst":"This is an example.",
                "rect":"79 122 201 37",
                "lineCount":1,
                "pasteImg":"xxx",
                "points":[
                    {
                        "x":50,
                        "y":50
                    },
                    {
                        "x":50,
                        "y":50
                    },
                    {
                        "x":20,
                        "y":20
                    },
                    {
                        "x":10,
                        "y":10
                    }
                ]
            }
        ],
        "sumSrc":"这是一个测试 这是一个例子 ",
        "sumDst":"This is a test. This is an example.",
        "pasteImg":"xxx"
    }
}*/

    private String errorCode;
    private String errorMsg;
    private DataDTO data;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String from;
        private String to;
        private List<ContentDTO> content;
        private String sumSrc;
        private String sumDst;
        private String pasteImg;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public List<ContentDTO> getContent() {
            return content;
        }

        public void setContent(List<ContentDTO> content) {
            this.content = content;
        }

        public String getSumSrc() {
            return sumSrc;
        }

        public void setSumSrc(String sumSrc) {
            this.sumSrc = sumSrc;
        }

        public String getSumDst() {
            return sumDst;
        }

        public void setSumDst(String sumDst) {
            this.sumDst = sumDst;
        }

        public String getPasteImg() {
            return pasteImg;
        }

        public void setPasteImg(String pasteImg) {
            this.pasteImg = pasteImg;
        }

        public static class ContentDTO {
            private String src;
            private String dst;
            private String rect;
            private Integer lineCount;
            private String pasteImg;
            private List<PointsDTO> points;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getDst() {
                return dst;
            }

            public void setDst(String dst) {
                this.dst = dst;
            }

            public String getRect() {
                return rect;
            }

            public void setRect(String rect) {
                this.rect = rect;
            }

            public Integer getLineCount() {
                return lineCount;
            }

            public void setLineCount(Integer lineCount) {
                this.lineCount = lineCount;
            }

            public String getPasteImg() {
                return pasteImg;
            }

            public void setPasteImg(String pasteImg) {
                this.pasteImg = pasteImg;
            }

            public List<PointsDTO> getPoints() {
                return points;
            }

            public void setPoints(List<PointsDTO> points) {
                this.points = points;
            }

            public static class PointsDTO {
                private Integer x;
                private Integer y;

                public Integer getX() {
                    return x;
                }

                public void setX(Integer x) {
                    this.x = x;
                }

                public Integer getY() {
                    return y;
                }

                public void setY(Integer y) {
                    this.y = y;
                }
            }
        }
    }

/*
* {
    "error_code":"0",
    "error_msg":"success",
    "data":{
        "from":"zh",
        "to":"en",
        "content":[
            {
                "src":"这是一个测试 ",
                "dst":"This is a test.",
                "rect":"79 23 246 43",
                "lineCount":1,
                "pasteImg":"xxx",
                "points":[
                    {
                        "x":0,
                        "y":0
                    },
                    {
                        "x":0,
                        "y":0
                    },
                    {
                        "x":20,
                        "y":20
                    },
                    {
                        "x":10,
                        "y":10
                    }
                ]
            },
            {
                "src":"这是一个例子 ",
                "dst":"This is an example.",
                "rect":"79 122 201 37",
                "lineCount":1,
                "pasteImg":"xxx",
                "points":[
                    {
                        "x":50,
                        "y":50
                    },
                    {
                        "x":50,
                        "y":50
                    },
                    {
                        "x":20,
                        "y":20
                    },
                    {
                        "x":10,
                        "y":10
                    }
                ]
            }
        ],
        "sumSrc":"这是一个测试 这是一个例子 ",
        "sumDst":"This is a test. This is an example.",
        "pasteImg":"xxx"
    }
}
* */

}
