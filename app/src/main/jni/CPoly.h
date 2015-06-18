class CPolynomialCalculatorCallback
{
    public:
        CPolynomialCalculatorCallback() {}
        virtual ~CPolynomialCalculatorCallback() {}
        virtual void getCoefficients(double* c0, double* c1, double* c2, double* c3)=0;
        void setValue(double& ref, double val) {
            ref = val;
        }
};

class CPolynomialCalculator
{
    public:

        void setCallback(CPolynomialCalculatorCallback* pCallback)
        {
            mCallback = pCallback;
        }

        double calculate(double x)
        {
            double c0 = 0;
            double c1 = 0;
            double c2 = 0;
            double c3 = 0;

            mCallback->getCoefficients(&c0, &c1, &c2, &c3);

            return c3 * x * x * x + c2 * x * x + c1 * x + c0;
        }

    protected:
        CPolynomialCalculatorCallback* mCallback;
};
