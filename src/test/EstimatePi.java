package test;


public class EstimatePi
{
protected int precision;
protected double circleRadiusSquared;

protected int data[], position = 0;
protected long count = 0, within = 0;


  public EstimatePi()
  {
    precision = 1;
    data = new int[precision*2];
    circleRadiusSquared = Math.pow( Math.pow( 256.0, precision ) - 1, 2.0 );
  }


  public EstimatePi( int precision )
  {
    this.precision = precision;
    data = new int[precision*2];
    circleRadiusSquared = Math.pow( Math.pow( 256.0, precision ) - 1, 2.0 );
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

    double xpos = 0.0, ypos = 0.0;

      for (int j=0; j<precision; j++)
      {
        xpos = xpos * 256.0 + data[j];
        ypos = ypos * 256.0 + data[j+precision];
      }

      if (xpos * xpos + ypos * ypos <= circleRadiusSquared)
        within++;
    }
  }


  public double getEstimate()
  {
    if (0 == count) return 0.0;

    return 4.0 * within / (double) count;
  }
}

