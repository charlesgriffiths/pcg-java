package rngtools;


// algorithm for skew correction by Von Neumann
public class SkewCorrectionFilter extends SeekableRNG
{
ISeekableRNG source;
RNGBitStream bitStream;


  private SkewCorrectionFilter() {}

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

      ret <<= 1;
      if (2 == datum)
        ret |= 1;
    }

    return ret;
  }


  @Override
  public short next16()
  {
    return (short) ((next8() << 8) | ((int)next8() & 0xff));
  }


  @Override
  public int next32()
  {
    return (next16() << 16) | ((int)next16() & 0xffff);
  }


  @Override
  public ISeekableRNG deepCopy()
  {
    return deepCopy( new SkewCorrectionFilter() );
  }


  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    if (target instanceof SkewCorrectionFilter)
    {
    SkewCorrectionFilter scf = (SkewCorrectionFilter) target;

      scf.source = source.deepCopy();
      if (bitStream == source && scf.source instanceof RNGBitStream)
        scf.bitStream = (RNGBitStream) scf.source;
      else
        scf.bitStream = new RNGBitStream( scf.source );
    }

    return target;
  }

}

