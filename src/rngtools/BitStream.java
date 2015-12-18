package rngtools;


abstract public class BitStream implements IBitStream
{
  protected BitStream()
  {
  }



  public static BitStream create( IBitStream bitStream )
  {
    return new BitStreamFromBitStream( bitStream );
  }


  public static BitStream create( IByteStream byteStream )
  {
    return new BitStreamFromByteStream( byteStream );
  }


  public static BitStream create( IRNG rng )
  {
    return create( ByteStream.create( rng ));
  }


  public int next( int bits )
  {
  int ret = 0;

    for (int i=0; i<bits; i++)
    {
      ret <<= 1;
      if (next()) ret |= 1;
    }

    return ret;
  }


  public long nextl( int bits )
  {
  long ret = 0;

    for (int i=0; i<bits; i++)
    {
      ret <<= 1;
      if (next()) ret |= 1;
    }

    return ret;
  }


  @Override
  public IBitStream deepCopy()
  {
    return this;
  }

}


class BitStreamFromBitStream extends BitStream
{
IBitStream bitStream;

  BitStreamFromBitStream( IBitStream bitStream )
  {
    this.bitStream = bitStream;
  }


  @Override
  public boolean next()
  {
    return bitStream.next();
  }
}


class BitStreamFromByteStream extends BitStream
{
IByteStream byteStream;
int datum, position;

  BitStreamFromByteStream( IByteStream byteStream )
  {
    this.byteStream = byteStream;
    datum = 0;
    position = -1;
  }


  @Override
  public boolean next()
  {
    if (position < 0)
    {
      datum = byteStream.next();
      position = 7;
    }

    return 0 != (datum & (1<<(position--)));
  }
  
}

