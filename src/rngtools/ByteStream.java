package rngtools;

abstract public class ByteStream implements IByteStream
{
  protected ByteStream() {}


  public static ByteStream create( IBitStream bitStream )
  {
    return new ByteStreamFromBitStream( bitStream );
  }


  public static ByteStream create( IByteStream byteStream )
  {
    return new ByteStreamFromByteStream( byteStream );
  }


  public static ByteStream create( IRNG rng )
  {
    return new ByteStreamFromRNG( rng );
  }


  public long next( int byteCount )
  {
  long ret = 0;

    for (int i=0; i<byteCount; i++)
    {
      ret <<= 8;
      ret |= next();
    }

    return ret;
  }


  public void next( byte b[] )
  {
    next( b, 0, b.length );
  }


  public void next( byte b[], int offset, int amount )
  {
    for (int i=0; i<amount; i++)
      b[offset+i] = (byte) next();
  }
}


class ByteStreamFromBitStream extends ByteStream
{
BitStream bitStream;

  ByteStreamFromBitStream( IBitStream bitStream )
  {
    if (bitStream instanceof BitStream)
      this.bitStream = (BitStream) bitStream;
    else
      this.bitStream = BitStream.create( bitStream );
  }


  @Override
  public int next()
  {
    return bitStream.next( 8 );
  }


  @Override
  public IByteStream deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }
}


class ByteStreamFromByteStream extends ByteStream
{
IByteStream byteStream;

  ByteStreamFromByteStream( IByteStream byteStream )
  {
    this.byteStream = byteStream;
  }


  @Override
  public int next()
  {
    return byteStream.next();
  }


  @Override
  public IByteStream deepCopy()
  {
    // TODO Auto-generated method stub
    return byteStream.deepCopy();
  }
}


class ByteStreamFromRNG extends ByteStream
{
IRNG rng;
byte data[];
int position;


  ByteStreamFromRNG( IRNG rng )
  {
    this.rng = rng;
    data = null;
    position = -1;
  }


  @Override
  public int next()
  {
    if (position < 0)
    {
      if (null == data)
        data = rng.next();
      else
        rng.next( data );

      position = data.length - 1;
    }

    return 0xff & (data[position--]);
  }


  @Override
  public IByteStream deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }
}


