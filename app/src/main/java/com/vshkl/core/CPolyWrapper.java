/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.vshkl.core;

public class CPolyWrapper {
  public static SWIGTYPE_p_double new_doublep() {
    long cPtr = CPolyWrapperJNI.new_doublep();
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public static SWIGTYPE_p_double copy_doublep(double value) {
    long cPtr = CPolyWrapperJNI.copy_doublep(value);
    return (cPtr == 0) ? null : new SWIGTYPE_p_double(cPtr, false);
  }

  public static void delete_doublep(SWIGTYPE_p_double obj) {
    CPolyWrapperJNI.delete_doublep(SWIGTYPE_p_double.getCPtr(obj));
  }

  public static void doublep_assign(SWIGTYPE_p_double obj, double value) {
    CPolyWrapperJNI.doublep_assign(SWIGTYPE_p_double.getCPtr(obj), value);
  }

  public static double doublep_value(SWIGTYPE_p_double obj) {
    return CPolyWrapperJNI.doublep_value(SWIGTYPE_p_double.getCPtr(obj));
  }

}
