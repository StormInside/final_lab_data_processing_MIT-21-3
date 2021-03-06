/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalLab.Class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 *
 * @author andrii
 */
@EnableAutoConfiguration
@Entity
public class RefrigeratorSection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "max_volume")
    private int maxVolume = 9;
    @Column(name = "type")
    private String type = "shelf"; // тип секции: дверца, или обычная полка
    @Column(name = "name")
    private String name = "sample_section_name";
    @Column(name = "contents_type")
    private String contentsType = "general";

    @ManyToOne
    @JoinColumn(name = "frige_id")
    private Refrigerator frige;

    @OneToMany(
            mappedBy = "inFrigeSection",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<Item> contents = new HashSet<Item>();

    public RefrigeratorSection() {
    }

    public RefrigeratorSection(String name) {
        this.name = name;
    }

    public RefrigeratorSection(int maxVolume, String type, String name, String contentsType) {
        this.maxVolume = maxVolume;
        this.type = type;
        this.name = name;
        this.contentsType = contentsType;
    }

    public RefrigeratorSection(int maxVolume, String type, String name, String contentsType, Set<Item> contents) {
        this.maxVolume = maxVolume;
        this.type = type;
        this.name = name;
        this.contentsType = contentsType;
        this.contents = contents;
    }
    
    public String getColor(){
        String color = "rgb(1, 82, 1)";
        if(this.getMaxVolume() - this.getContents().size()<=3){
            color =  "orange";
        }
        if(this.getMaxVolume() - this.getContents().size()<=0){
            color =  "rgb(170, 0, 0)";
        }
        return color;
    }
    
    /**
     * @param itemToAdd
     * @throws jdk.jshell.spi.ExecutionControl.NotImplementedException
     */
    public void addItem(Item itemToAdd) throws Exception {
        if (this.getVolume() + itemToAdd.getVolume() <= maxVolume) {
            contents.add(itemToAdd);
        } else {
            throw new Exception("TODO maxVolume");
        }

    }

    /**
     * @param itemToRemove
     */
    public void removeItem(Item itemToRemove) {
        contents.remove(itemToRemove);
    }

    /**
     * @return the volume
     * @throws jdk.jshell.spi.ExecutionControl.NotImplementedException
     */
    public int getVolume() throws NotImplementedException {
        int volume = 0;
        for (Item contentItem : contents) {
            volume += contentItem.getVolume();
        }
        return volume;
    }

    /**
     * @return the maxVolume
     */
    public int getMaxVolume() {
        return maxVolume;
    }

    /**
     * @param maxVolume the maxVolume to set
     */
    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }

    /**
     * @return the ContentsType
     */
    public String getContentsType() {
        return contentsType;
    }

    /**
     * @param contentsType
     */
//    class Sortbyroll implements Comparator<Item> 
//    { 
//
//        public int compare(Item a, Item b) 
//        { 
//            Date currentDate = new Date();
//            
//            long shelfLifeA = a.getShelfLife()* 86400000;
//            long A = currentDate.getTime() - a.getManufactureDate().getTime() + shelfLifeA;
//            
//            long shelfLifeB = b.getShelfLife()* 86400000;
//            long B = currentDate.getTime() - b.getManufactureDate().getTime() + shelfLifeB;
//            
//            return (int)B - (int)A; 
//        } 
//    } 
    
    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    /**
     * @return the contents
     */
    public List<Item> getContents() {
        List<Item> list = new ArrayList<Item>(this.contents);
        Comparator<Item> itemLifeComparator = Comparator.comparing(Item::getDateInt);
        Collections.sort(list, itemLifeComparator);
        return list;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(Set<Item> contents) {
        this.contents = contents;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the frige
     */
    public Refrigerator getFrige() {
        return frige;
    }

    /**
     * @param frige the frige to set
     */
    public void setFrige(Refrigerator frige) {
        this.frige = frige;
    }

}
