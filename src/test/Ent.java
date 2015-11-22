package test;

import java.util.Arrays;

// This class based on Ent - A Pseudorandom Number Sequence Test Program
// which was released into the public domain by John Walker
// January 28th, 2008 according to ftp://ftp.fourmilab.ch/web/random/index.html

public class Ent
{
final private double log2of10 = 3.32192809488736234787;

long total = 0, count[] = new long[256];
int mcPiDimension = 6, mcPiPosition = 0, mcPiData[], mcPiCount = 0, mcPiWithin = 0;
double circleRadiusSquared;

double sccun, sccu0, scclast;
double scct1 = 0.0, scct2 = 0.0, scct3 = 0.0; // serial correlation terms

double entropy = 0.0, chisq = 0.0, mean = 0.0, mpcalc = 0.0, scc = 0.0;


  public Ent()
  {
    initialize();
  }

  public Ent( int piDimension )
  {
    if (piDimension > 1 && piDimension < 10)
      mcPiDimension = piDimension;

    initialize();
  }


  protected void initialize()
  {
    Arrays.fill( count, 0L );
    mcPiData = new int[mcPiDimension];

    circleRadiusSquared = Math.pow( Math.pow( 256.0, mcPiDimension / 2 ) - 1, 2.0 );
  }


  public void addBytes( byte b[] )
  {
    for (int i=0; i<b.length; i++)
    {
    int datum = b[i] & 0xff;

      total++;
      count[datum]++;
      mcPiData[mcPiPosition++] = datum;
      if (mcPiData.length == mcPiPosition)
      {
        mcPiPosition = 0;
        mcPiCount++;

      double xpos = 0.0, ypos = 0.0;

        for (int j=0; j<mcPiDimension/2; j++)
        {
          xpos = xpos * 256.0 + mcPiData[j];
        }
        for (int j=mcPiDimension/2; j<mcPiDimension; j++)
        {
          ypos = ypos * 256.0 + mcPiData[j];
        }

        if (xpos * xpos + ypos * ypos <= circleRadiusSquared)
          mcPiWithin++;
      }


      /* Update calculation of serial correlation coefficient */

      sccun = datum;
      if (1 == total)  // first datum
      {
        scclast = 0;
        sccu0 = sccun;
      }
      else
      {
        scct1 = scct1 + scclast * sccun;
      }
      scct2 = scct2 + sccun;
      scct3 = scct3 + (sccun * sccun);
      scclast = sccun;
    }
  }


  public void finish()
  {
  /* Complete calculation of serial correlation coefficient */

    scct1 = scct1 + scclast * sccu0;
    scct2 = scct2 * scct2;
    scc = total * scct3 - scct2;
    if (0.0 == scc)
      scc = -100000;
    else
      scc = (total * scct1 - scct2) / scc;


  double expected = total / 256.0;
  long datasum = 0L;

    for (int i=0; i<256; i++)
    {
    double a = count[i] - expected;

      chisq += a * a / expected;
      datasum += i * count[i];

      /* Calculate entropy */
    double probi = count[i] / (double) total;

      if (probi > 0.0)
        entropy += probi * Math.log10( 1 / probi );
    }
    mean = datasum / (double) total;
    entropy *= log2of10;


    mpcalc = 4.0 * mcPiWithin / (double) mcPiCount;
  }

  
  double zprob( double z )
  {
  final double zmax = 6.0;
  double w = 0.0, x = 0.0, y = 0.0;
  
    if (0.0 != z)
    {
      y = 0.5 * Math.abs( z );
      if (y >= (zmax * 0.5))
      {
        x = 1.0;
      }
      else if (y < 1.0)
      {
        w = y * y;
        x = ((((((((0.000124818987 * w
            -0.001075204047) * w +0.005198775019) * w
            -0.019198292004) * w +0.059054035642) * w
            -0.151968751364) * w +0.319152932694) * w
            -0.531923007300) * w +0.797884560593) * y * 2.0;
      }
      else
      {
        y -= 2.0;
        x = (((((((((((((-0.000045255659 * y
                 +0.000152529290) * y -0.000019538132) * y
                 -0.000676904986) * y +0.001390604284) * y
                 -0.000794620820) * y -0.002034254874) * y
                 +0.006549791214) * y -0.010557625006) * y
                 +0.011630447319) * y -0.009279453341) * y
                 +0.005353579108) * y -0.002141268741) * y
                 +0.000535310849) * y +0.999936657524;
      }
    }
  
    return (z > 0.0 ? ((x + 1.0) * 0.5) : ((1.0 - x) * 0.5));
  }

  double ex(double x) { return ((x < -20) ? 0.0 : Math.exp( x )); }
  public double chiSqProb( double ax, int df )
  {
  final double LOG_SQRT_PI = 0.5723649429247000870717135;
  final double I_SQRT_PI = 0.5641895835477562869480795;
  final double BIGX = 20.0;
  
    if (ax <= 0.0 || df < 1) return 1.0;

  double x = ax;
  double a = 0.5 * x, y=0, s;
  double e, c, z;
  boolean even = 0==df%2;
  

    if (df > 1)
      y = ex( -a );

    s = (even ? y : (2.0 * zprob( -Math.sqrt( x ))));
    if (df > 2)
    {
      x = 0.5 * (df - 1.0);
      z = (even ? 1.0 : 0.5);
      if (a > BIGX)
      {
        e = (even ? 0.0 : LOG_SQRT_PI);
        c = Math.log( a );
        while (z <= x)
        {
          e = Math.log( z ) + e;
          s += ex(c * z - a - e);
          z += 1.0;
        }

        return s;
      }
      else
      {
        e = (even ? 1.0 : (I_SQRT_PI / Math.sqrt( a )));
        c = 0.0;
        while (z <= x)
        {
          e = e * (a / z);
          c = c + e;
          z += 1.0;
        }

        return c * y + s;
      }
    }

    return s;
  }



  

  public double getEntropy() { return entropy; }
  public double getChiSq() { return chisq; }
  public double getChiSqProb() { return chiSqProb( chisq, 255 ); }
  public double getMean() { return mean; }
  public double getMontePiCalc() { return mpcalc; }
  public double getScc() { return scc; }


  public String toString()
  {
    return "entropy: " + getEntropy() + " chi sq: " + getChiSq() + "("+getChiSqProb()+")  mean: " + getMean() + " pi: " + getMontePiCalc() + " scc: " + getScc();
  }
}

