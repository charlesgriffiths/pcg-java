package pcg.rng;


public class RNGBitStream implements ISeekableRNG
{
ISeekableRNG source;
RNGStream byteStream;
int bitPosition = 0;
int datum = 0;

  private RNGBitStream() {}
  public RNGBitStream( ISeekableRNG rng )
  {
    source = rng;
    if (rng instanceof RNGStream)
      byteStream = (RNGStream) rng;
    else
      byteStream = new RNGStream( rng );
  }

  @Override
  public void setState( byte[] b )
  {
    byteStream.setState( b );
  }

  @Override
  public boolean seek( long position )
  {
    if (byteStream.seek( position / 8 ))
    {
      bitPosition = (int) (position % 8);
      return true;
    }

    return false;
  }

  @Override
  public void advance( long amount )
  {
    // TODO Auto-generated method stub

  }

  @Override
  public int blockSize()
  {
    return 1;
  }

  @Override
  public long skipBytes( long amount )
  {
    // TODO Auto-generated method stub
    return 0;
  }


  public boolean next()
  {
    if (0 == bitPosition)
    {
      bitPosition = 8;
      datum = byteStream.next8();
    }

    bitPosition--;

    return 0 != (datum & (1<<bitPosition));
  }


  public int next( int bits )
  {
  int ret = 0;

    for (int i=0; i<bits; i++)
    {
      if (next()) ret |= 1;
      ret <<= 1;
    }

    return ret;
  }


  public int nextl( int bits )
  {
  int ret = 0;

    for (int i=0; i<bits; i++)
    {
      if (next()) ret |= 1;
      ret <<= 1;
    }

    return ret;
  }


  @Override
  public byte next8()
  {
    // TODO Auto-generated method stub
    return 0;
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
  public long next64()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void next( byte[] b )
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void next( byte[] b, int offset, int length )
  {
    // TODO Auto-generated method stub

  }

  @Override
  public ISeekableRNG deepCopy()
  {
    // TODO Auto-generated method stub
    return deepCopy( new RNGBitStream() );
  }


  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    if (target instanceof RNGBitStream)
    {
    RNGBitStream rbs = new RNGBitStream();

      rbs.source = source.deepCopy();
      if (byteStream == source && rbs.source instanceof RNGStream)
        rbs.byteStream = (RNGStream) rbs.source;
      else
        rbs.byteStream = new RNGStream( rbs.source );

      rbs.bitPosition = bitPosition;
      rbs.datum = datum;
    }

    return target;
  }


  @Override
  public void setState( long seed )
  {
    // TODO Auto-generated method stub

  }

}

