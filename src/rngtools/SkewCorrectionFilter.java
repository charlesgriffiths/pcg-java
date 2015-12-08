package rngtools;


// algorithm for skew correction by Von Neumann
public class SkewCorrectionFilter extends BitStream
{
IBitStream bitStream;


  protected SkewCorrectionFilter() {}

  public SkewCorrectionFilter( IBitStream ibs )
  {
    bitStream = ibs;
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

