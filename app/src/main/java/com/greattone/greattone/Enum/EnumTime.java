package com.greattone.greattone.Enum;

/**
 * Created by Administrator on 2016/10/31.
 */
public enum EnumTime {
    TIME1("06:00",0),
    TIME2("06:15",1),
    TIME3("06:30",2),
    TIME4("06:45",3),
    TIME5("07:00",4),
    TIME6("07:15",5),
    TIME7("07:30",6),
    TIME8("07:45",7),
    TIME9("08:00",8),
    TIME10("08:15",9),
    TIME11("08:30",10),
    TIME12("08:45",11),
    TIME13("09:00",12),
    TIME14("09:15",13),
    TIME15("09:30",14),
    TIME16("09:45",15),
    TIME17("10:00",16),
    TIME18("10:15",17),
    TIME19("10:30",18),
    TIME20("10:45",19),
    TIME21("11:00",20),
    TIME22("11:15",21),
    TIME23("11:30",22),
    TIME24("11:45",23),
    TIME25("12:00",24),
    TIME26("12:15",25),
    TIME27("12:30",26),
    TIME28("12:45",27),
    TIME29("13:00",28),
    TIME30("13:15",29),
    TIME31("13:30",30),
    TIME32("13:45",31),
    TIME33("14:00",32),
    TIME34("14:15",33),
    TIME35("14:30",34),
    TIME36("14:45",35),
    TIME37("15:00",36),
    TIME38("15:15",37),
    TIME39("15:30",38),
    TIME40("15:45",39),
    TIME41("16:00",40),
    TIME42("16:15",41),
    TIME43("16:30",42),
    TIME44("16:45",43),
    TIME45("17:00",44),
    TIME46("17:15",45),
    TIME47("17:30",46),
    TIME48("17:45",47),
    TIME49("18:00",48),
    TIME50("18:15",49),
    TIME51("18:30",50),
    TIME52("18:45",51),
    TIME53("19:00",52),
    TIME54("19:15",53),
    TIME55("19:30",54),
    TIME56("19:45",55),
    TIME57("20:00",56),
    TIME58("20:15",57),
    TIME59("20:30",58),
    TIME60("20:45",59),
    TIME61("21:00",60),
    TIME62("21:15",61),
    TIME63("21:30",62),
    TIME64("21:45",63),
    TIME65("22:00",64),
    TIME66("22:15",65),
    TIME67("22:30",66),
    TIME68("22:45",67),
    TIME69("23:00",68);

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
