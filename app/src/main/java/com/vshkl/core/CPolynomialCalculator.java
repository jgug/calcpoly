/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vshkl.core;

public class CPolynomialCalculator {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CPolynomialCalculator(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CPolynomialCalculator obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CPolyWrapperJNI.delete_CPolynomialCalculator(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setCallback(CPolynomialCalculatorCallback pCallback) {
    CPolyWrapperJNI.CPolynomialCalculator_setCallback(swigCPtr, this, CPolynomialCalculatorCallback.getCPtr(pCallback), pCallback);
  }

  public double calculate(double x) {
    return CPolyWrapperJNI.CPolynomialCalculator_calculate(swigCPtr, this, x);
  }

  public CPolynomialCalculator() {
    this(CPolyWrapperJNI.new_CPolynomialCalculator(), true);
  }

}
