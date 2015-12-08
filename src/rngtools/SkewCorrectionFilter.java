package rngtools;


// algorithm for skew correction by Von Neumann
public class SkewCorrectionFilter extends BitStream
{
BitStream bitStream;


  protected SkewCorrectionFilter() {}

  public SkewCorrectionFilter( BitStream bitStream )
  {
    this.bitStream = bitStream;
  }


  @Override
  public boolean next()
  {
  int datum;

    do
    {
      datum = bitStream.next( 2 );
    }
    while (0 == datum || 3 == datum);

    return 2 == datum;
  }
}

