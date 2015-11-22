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


  public double getEntropy() { return entropy; }
  public double getChiSq() { return chisq; }
  public double getMean() { return mean; }
  public double getMontePiCalc() { return mpcalc; }
  public double getScc() { return scc; }


  public String toString()
  {
    return "entropy: " + getEntropy() + " chi sq: " + getChiSq() + " mean: " + getMean() + " pi: " + getMontePiCalc() + " scc: " + getScc();
  }
}

