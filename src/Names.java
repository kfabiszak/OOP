/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ja
 */
public enum Names {
    N1("Roman"),
    N2("Zbigniew"),
    N3("Joanna"),
    N4("Katarzyna"),
    N5("Zofia"),
    N6("Antoni"),
    N7("Krzysztof"),
    N8("Tomasz"),
    N9("Anna"),
    N10("Anita"),
    N11("Hanna"),
    N12("Dariusz"),
    N13("Wiesław"),
    N14("Wiesława"),
    N15("Kazimiera"),
    N16("Małgorzata"),
    N17("Jarosław"),
    N18("Kamil"),
    N19("Aleksandra"),
    N20("Julia"),
    N21("Julita"),
    N22("Weronika"),
    N23("Henryk"),
    N24("Danuta"),
    N25("Magda"),
    N26("Agata");
    
    private final String name;
    
    private Names(String name){
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
