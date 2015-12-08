package rngtools;


abstract public class BitStream implements IBitStream
{
  protected BitStream()
  {
  }


  public static IBitStream create( ISeekableRNG rng )
  {
    return create( ByteStream.create( rng ));
  }


  public static IBitStream create( IByteStream byteStream )
  {
    return new BitStreamFromByteStream( byteStream );
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
  public long nextl( int bits )
  {
  long ret = 0;

    for (int i=0; i<bits; i++)
    {
      if (next()) ret |= 1;
      ret <<= 1;
    }

    return ret;
  }


  @Override
  public IBitStream split()
  {
    return this;
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

