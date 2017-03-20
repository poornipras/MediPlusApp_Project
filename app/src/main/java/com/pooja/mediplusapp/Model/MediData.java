package com.pooja.mediplusapp.Model;

/**
 * Created by Pooja on 2/27/2017.
 */

public class MediData
{
    String name,description,price,medi_form,instructions;
    int id;
    String Category;

    public MediData(String name, String description, String price, String medi_form, String instructions, int id, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.medi_form = medi_form;
        this.instructions = instructions;
        this.id = id;
        Category = category;
    }

    public String getMedi_form() {
        return medi_form;
    }

    public void setMedi_form(String medi_form) {
        this.medi_form = medi_form;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }


}
