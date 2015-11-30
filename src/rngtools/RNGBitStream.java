package rngtools;


public class RNGBitStream implements IRNGBitStream
{
ISeekableRNG source;
RNGStream byteStream;
int bitPosition = 0;
int datum = 0;


  protected RNGBitStream() {}
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
      datum = byteStream.next8();

      return true;
    }

    return false;
  }


  @Override
  public void advance( long amount )
  {
    // TODO: advance amount of bits or bytes?
  }


  @Override
  public int blockSize()
  {
    return 1;
  }


  @Override
  public long skipBytes( long amount )
  {
    byteStream.skipBytes( amount );
    datum = byteStream.next8();

    return amount;
  }


  @Override
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


  @Override
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


  @Override
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
    return (byte) next( 8 );
  }


  @Override
  public short next16()
  {
    return (short) next( 16 );
  }


  @Override
  public int next32()
  {
    return next( 32 );
  }


  @Override
  public long next64()
  {
    return ((long) next32() << 32) | ((long) next32() & 0xffffffff);
  }


  @Override
  public void next( byte[] b )
  {
    next( b, 0, b.length );
  }


  @Override
  public void next( byte[] b, int offset, int length )
  {
    for (int i=0; i<length; i++)
      b[offset+i] = next8();
  }


  @Override
  public ISeekableRNG deepCopy()
  {
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
    byteStream.setState( seed );
  }

}

