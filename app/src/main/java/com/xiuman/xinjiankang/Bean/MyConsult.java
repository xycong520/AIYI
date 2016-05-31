package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：自我诊断
 * Created by hxy on 2015/8/30.
 */
public class MyConsult implements Serializable{
    private static final long serialVersionUID = 566113516997068988L;
    /**
     * id : 402890234f7d867d014f7da6d3820001
     * name : 妇科
     * diseaseCategory : [{"diseaseCategoryName":"盆腔炎","diseaseCategoryId":"402890234f7d867d014f7db8c3040005"}]
     */
    private String id;
    private String name;
    private List<DiseaseCategoryEntity> diseaseCategory;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiseaseCategory(List<DiseaseCategoryEntity> diseaseCategory) {
        this.diseaseCategory = diseaseCategory;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DiseaseCategoryEntity> getDiseaseCategory() {
        return diseaseCategory;
    }

    public static class DiseaseCategoryEntity implements Serializable{
        /**
         * diseaseCategoryName : 盆腔炎
         * diseaseCategoryId : 402890234f7d867d014f7db8c3040005
         */

        private String diseaseCategoryName;
        private String diseaseCategoryId;

        public void setDiseaseCategoryName(String diseaseCategoryName) {
            this.diseaseCategoryName = diseaseCategoryName;
        }

        public void setDiseaseCategoryId(String diseaseCategoryId) {
            this.diseaseCategoryId = diseaseCategoryId;
        }

        public String getDiseaseCategoryName() {
            return diseaseCategoryName;
        }

        public String getDiseaseCategoryId() {
            return diseaseCategoryId;
        }
    }
}
