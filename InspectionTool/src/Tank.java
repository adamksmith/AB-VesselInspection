
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import java.sql.Timestamp;
import java.util.SimpleTimeZone;

public class Tank {
    private String tankID;
    private String state;
    private Date date;
    private String initials;
    private Boolean pastDue;
    public Tank(String tankID, String state, Date date, String initials){
        this.tankID = tankID;
        this.state = state;
        this.date = date;
        this.initials = initials;
        this.pastDue = past(date);
    }

    public boolean equals(Tank t){
        if(tankID == t.getTankID())
            return true;
        else
            return false;
    }
    public String getTankID(){
        return tankID;
    }
    public String getState(){return state;}
    public Date getDate(){return date;}
    public String getInitials(){return initials;}
    public Boolean isPast(){return pastDue;}

    @Override
    public String toString(){
        return tankID + state + date + initials;
    }
    public boolean past(Date date1){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(cal.getTimeZone());
        String stringDate = dateFormat.format(date1);
        Date pastDue1 = cal.getTime();

        try {
            if(new SimpleDateFormat("dd/MM/yyyy").parse(stringDate).before(pastDue1)){

                return true;
            }
    //        ZonedDateTime thirtyDaysAgo = ZonedDateTime.now().plusDays(-30);
    //        if(date.toInstant().isBefore(thirtyDaysAgo.toInstant())){
    //            return true;
    //        }
            else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
