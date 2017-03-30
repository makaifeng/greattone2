package com.greattone.greattone.Enum;

/**
 * Created by Administrator on 2016/10/31.
 */
public enum EnumTime {
//    TIME1("06:00",0),
//    TIME2("06:15",1),
//    TIME3("06:30",2),
//    TIME4("06:45",3),
//    TIME5("07:00",4),
//    TIME6("07:15",5),
//    TIME7("07:30",6),
//    TIME8("07:45",7),
    TIME9("08:00",0),
    TIME10("08:15",1),
    TIME11("08:30",2),
    TIME12("08:45",3),
    TIME13("09:00",4),
    TIME14("09:15",5),
    TIME15("09:30",6),
    TIME16("09:45",7),
    TIME17("10:00",8),
    TIME18("10:15",9),
    TIME19("10:30",10),
    TIME20("10:45",11),
    TIME21("11:00",12),
    TIME22("11:15",13),
    TIME23("11:30",14),
    TIME24("11:45",15),
    TIME25("12:00",16),
    TIME26("12:15",17),
    TIME27("12:30",18),
    TIME28("12:45",19),
    TIME29("13:00",20),
    TIME30("13:15",21),
    TIME31("13:30",22),
    TIME32("13:45",23),
    TIME33("14:00",24),
    TIME34("14:15",25),
    TIME35("14:30",26),
    TIME36("14:45",27),
    TIME37("15:00",28),
    TIME38("15:15",29),
    TIME39("15:30",30),
    TIME40("15:45",31),
    TIME41("16:00",32),
    TIME42("16:15",33),
    TIME43("16:30",34),
    TIME44("16:45",35),
    TIME45("17:00",36),
    TIME46("17:15",37),
    TIME47("17:30",38),
    TIME48("17:45",39),
    TIME49("18:00",40),
    TIME50("18:15",41),
    TIME51("18:30",42),
    TIME52("18:45",43),
    TIME53("19:00",44),
    TIME54("19:15",45),
    TIME55("19:30",46),
    TIME56("19:45",47),
    TIME57("20:00",48),
    TIME58("20:15",49),
    TIME59("20:30",50),
    TIME60("20:45",51),
    TIME61("21:00",52);
//    TIME62("21:15",61),
//    TIME63("21:30",62),
//    TIME64("21:45",63);
//    TIME65("22:00",64),
//    TIME66("22:15",65),
//    TIME67("22:30",66),
//    TIME68("22:45",67),
//    TIME69("23:00",68);

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    String time;
    int position;

    EnumTime(String time, int position) {
        this.time=time;
        this.position=position;
    }

}
