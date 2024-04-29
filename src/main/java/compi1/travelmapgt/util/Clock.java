
package compi1.travelmapgt.util;

import compi1.travelmapgt.models.PathInfo;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

/**
 *
 * @author yennifer
 */
public class Clock implements Runnable{
    private LocalTime currentTime;
    private JLabel displayTime;
    private boolean active;
    
    public Clock(JLabel displayTime) {
        this.displayTime = displayTime;
        currentTime = LocalTime.now();
        active = true;
    }
    
    public void stop(){
        this.active = false;
    }
    
    public void restart(){
        this.active = true;
    }
    
    public void adjust(LocalTime time){
        this.currentTime = time;
    }
    
    public void restartHour(){
        this.currentTime = LocalTime.now();
    }
    
    public void setDisplayTime(JLabel display){
        this.displayTime = display;
    }
    
    public LocalTime getCurrentTime(){
        return this.currentTime;
    }
    
    public int calculateTraficProbability(LocalTime currentTime, PathInfo pathInfo){
        if(pathInfo.getHourInitTrafic() == null || pathInfo.getHourFinishTrafic() == null){
            return 0;
        }
        if (currentTime.isAfter(pathInfo.getHourInitTrafic()) //dentro del rango
                && currentTime.isBefore(pathInfo.getHourFinishTrafic())) {
            return pathInfo.getProbabilityTrafic();
        } else {
            return 0;
        }
    }
    
    public boolean isActive(){
        return this.active;
    }

    @Override
    public void run() {
        while (true) {
            if(active){
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
                String horaActual = formato.format(currentTime);
                displayTime.setText(horaActual);
                displayTime.repaint();
                try {
                    currentTime = currentTime.plusSeconds(1);
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException ex) {
                    /*controlado*/
                }
            }
        }
    }
}
