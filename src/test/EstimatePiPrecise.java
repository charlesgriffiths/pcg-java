package test;

import java.math.BigDecimal;
import java.math.BigInteger;


public class EstimatePiPrecise
{
protected int precision;
protected BigInteger circleRadiusSquared;

protected int data[], position = 0;
protected long count = 0, within = 0;
private BigInteger B256 = BigInteger.valueOf( 256 );

  public EstimatePiPrecise()
  {
    precision = 1;
    initialize();
  }


  public EstimatePiPrecise( int precision )
  {
    this.precision = precision;
    initialize();
  }


  private void initialize()
  {
    data = new int[precision*2];
//    circleRadiusSquared = Math.pow( Math.pow( 256.0, precision ) - 1, 2.0 );
    circleRadiusSquared = B256.pow( precision ).subtract( BigInteger.ONE  ).pow( 2 );
  }


  public void add( byte b[] )
  {
    for (int i=0; i<b.length; i++)
      add( b[i] );
  }

  public void add( int d )
  {
    add( (byte) (d >> 24) );
    add( (byte) (d >> 16) );
    add( (byte) (d >> 8) );
    add( (byte) d );
  }

  public void add( byte b )
  {
  int datum = b & 0xff;

    data[position++] = datum;
    if (data.length == position)
    {
      position = 0;
      count++;

//    double xpos = 0.0, ypos = 0.0;
    BigInteger xpos = BigInteger.ZERO, ypos = BigInteger.ZERO;

      for (int j=0; j<precision; j++)
      {
//        xpos = xpos * 256.0 + data[j];
//        ypos = ypos * 256.0 + data[j+precision];
        xpos = xpos.multiply( B256 ).add( BigInteger.valueOf( data[j] ));
        ypos = ypos.multiply( B256 ).add( BigInteger.valueOf( data[j+precision] ));
      }

//      if (xpos * xpos + ypos * ypos <= circleRadiusSquared)
      if (circleRadiusSquared.compareTo( xpos.pow( 2 ).add( ypos.pow( 2 ))) >= 0)
        within++;
    }
  }

private BigDecimal BD4 = BigDecimal.valueOf( 4 );
  public double getEstimate()
  {
    if (0 == count) return 0.0;

//    return 4.0 * within / (double) count;
    return BD4.multiply( BigDecimal.valueOf( within ) ).divide( BigDecimal.valueOf( count ), 100, BigDecimal.ROUND_HALF_DOWN ).doubleValue();
  }
}

