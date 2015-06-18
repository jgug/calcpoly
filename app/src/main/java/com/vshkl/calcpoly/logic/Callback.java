package com.vshkl.calcpoly.logic;

import com.vshkl.core.CPolynomialCalculatorCallback;
import com.vshkl.core.SWIGTYPE_p_double;

public class Callback extends CPolynomialCalculatorCallback {

    private double c0;
    private double c1;
    private double c2;
    private double c3;

    public Callback(double c0, double c1, double c2, double c3) {
        this.c0 = c0;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    @Override
    public void getCoefficients(SWIGTYPE_p_double c0, SWIGTYPE_p_double c1, SWIGTYPE_p_double c2, SWIGTYPE_p_double c3) {
        setValue(c0, this.c0);
        setValue(c1, this.c1);
        setValue(c2, this.c2);
        setValue(c3, this.c3);
    }

}
