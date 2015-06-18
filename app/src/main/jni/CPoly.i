/* File : CPoly.i */
%module(directors=1) CPolyWrapper
%include "cpointer.i"

/* Anything in the following section is added verbatim to the .cxx wrapper file */
%{
#include "CPoly.h"
%}

/* turn on director wrapping Callback */
%feature("director") CPolynomialCalculatorCallback;

/* This is the list of headers to be wrapped */
%include "CPoly.h"
%pointer_functions(double, doublep);