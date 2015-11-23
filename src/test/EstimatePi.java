package test;


public class EstimatePi
{
protected int precision;
protected double circleRadiusSquared;

protected int data[];
protected int position = 0, count = 0, within = 0;


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


  public void addBytes( byte b[] )
  {
    for (int i=0; i<b.length; i++)
    {
    int datum = b[i] & 0xff;

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
  }


  public double getEstimate()
  {
    if (0 == count) return 0.0;

    return 4.0 * within / (double) count;
  }
}

