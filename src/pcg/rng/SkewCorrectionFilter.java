package pcg.rng;


// algorithm for skew correction by Von Neumann
public class SkewCorrectionFilter extends SeekableRNG
{
ISeekableRNG source;
RNGBitStream bitStream;


  public SkewCorrectionFilter( ISeekableRNG rng )
  {
    source = rng;
    if (rng instanceof RNGBitStream)
      bitStream = (RNGBitStream) rng;
    else
      bitStream = new RNGBitStream( rng );
  }


  @Override
  public void setState( byte[] b )
  {
    bitStream.setState( b );
  }


  @Override
  public boolean seek( long position )
  {
    // TODO Auto-generated method stub
    return false;
  }


  @Override
  public void advance( long amount )
  {
    // TODO Auto-generated method stub

  }


  @Override
  public byte next8()
  {
  byte ret = 0;
  
    for (int bits=0; bits<8; bits++)
    {
    int datum;

      do
      {
        datum = bitStream.next( 2 );
      }
      while (0 == datum || 3 == datum);

      if (2 == datum)
        ret |= 1;

      ret <<= 1;
    }

    return ret;
  }


  @Override
  public short next16()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int next32()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ISeekableRNG deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    // TODO Auto-generated method stub
    return null;
  }

}

