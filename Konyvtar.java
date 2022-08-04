/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Konyv;
import pojos.Tag;

/**
 *
 * @author Katalin
 */
@ManagedBean
@SessionScoped
public class Konyvtar {

    private List<Tag> tagok;
    private List<Konyv> konyvek;
    private Tag kivalasztottTag;
    private int kivalasztottTagId;
    private Konyv kivalasztottKonyv;
    private String nevKeres;
    private List<Konyv> kolcsonozhetoList;
    private Tag kolcsonozheto;
     private Map<Integer, Tag> tagMap;

    public Konyvtar() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        kolcsonozhetoList = session.createQuery("From Konyv Where tag_id=0 ORDER BY szerzo").list();
        kolcsonozheto = (Tag) session.createQuery("From Tag Where id=0").uniqueResult();
        tagok = session.createQuery("From Tag Where id>0 ORDER BY nev").list();
        
        tagMap = new HashMap<>();
        for (Tag t : tagok) {
           tagMap.put(t.getId(), t);
        }

        session.close();
    }

    public void keres() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();

        Query q = session.createQuery("From Tag Where id > 0 AND nev LIKE :pnev ORDER BY nev");
        q.setString("pnev", nevKeres + "%");
        tagok = q.list();

        session.close();
    }

    public void konyvListaz(Tag tag) {
        kivalasztottTag = tag;
        konyvek = tag.getKonyvs();
    }

    public void visszavesz(Konyv konyv) {
        kivalasztottKonyv = konyv;
        kivalasztottKonyv.setTag(kolcsonozheto);
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(kivalasztottKonyv);
        session.getTransaction().commit();
        session.close();
        konyvek.remove(konyv);
        kolcsonozhetoList.add(konyv);

    }
    
    public void kivalaszt(){
        kivalasztottTag = tagMap.get(kivalasztottTagId);
        
    }

    public void kolcsonoz(Konyv konyv) {
        kivalasztottKonyv = konyv;
        kivalasztottKonyv.setTag(kivalasztottTag);
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(kivalasztottKonyv);
        session.getTransaction().commit();
        session.close();
        kolcsonozhetoList.remove(konyv);
        konyvek.add(konyv);
    }
    
       public String tagMent() {
        boolean uj = kivalasztottTag.getId() == null;
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        session.saveOrUpdate(kivalasztottTag);
        session.getTransaction().commit();
        session.close();
        
        if (uj) {
            tagok.add(kivalasztottTag);
            
        }
        
        return "index";
    }
       
         public String ujTag() {
        if (kivalasztottTag!=null) {
             kivalasztottTag = new Tag();
             kivalasztottTag.setNev(kivalasztottTag.getNev());
             kivalasztottTag.setEmail(kivalasztottTag.getEmail());
         return "ujTag";
        }
             
        return "ujTag";
    }
    
    
    //szerkeszt - töröl
    public List<Tag> getTagok() {
        return tagok;
    }

    public void setTagok(List<Tag> tagok) {
        this.tagok = tagok;
    }

    public List<Konyv> getKonyvek() {
        return konyvek;
    }

    public void setKonyvek(List<Konyv> konyvek) {
        this.konyvek = konyvek;
    }

    public Tag getKivalasztottTag() {
        return kivalasztottTag;
    }

    public void setKivalasztottTag(Tag kivalasztottTag) {
        this.kivalasztottTag = kivalasztottTag;
    }

    public Konyv getKivalasztottKonyv() {
        return kivalasztottKonyv;
    }

    public void setKivalasztottKonyv(Konyv kivalasztottKonyv) {
        this.kivalasztottKonyv = kivalasztottKonyv;
    }

    public String getNevKeres() {
        return nevKeres;
    }

    public void setNevKeres(String nevKeres) {
        this.nevKeres = nevKeres;
    }

    public Tag getKolcsonozheto() {
        return kolcsonozheto;
    }

    public void setKolcsonozheto(Tag kolcsonozheto) {
        this.kolcsonozheto = kolcsonozheto;
    }

    public List<Konyv> getKolcsonozhetoList() {
        return kolcsonozhetoList;
    }

    public void setKolcsonozhetoList(List<Konyv> kolcsonozhetoList) {
        this.kolcsonozhetoList = kolcsonozhetoList;
    }

    public int getKivalasztottTagId() {
        return kivalasztottTagId;
    }

    public void setKivalasztottTagId(int kivalasztottTagId) {
        this.kivalasztottTagId = kivalasztottTagId;
    }
}
