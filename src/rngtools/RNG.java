package rngtools;


abstract public class RNG implements IRNG
{
  protected RNG() {}


  public static RNG create( IBitStream bitStream )
  {
    return create( ByteStream.create( bitStream ));
  }


  public static RNG create( IByteStream byteStream )
  {
    return new RNGFromByteStream( byteStream );
  }


  public static RNG create( IRNG rng )
  {
    return create( ByteStream.create( rng ));
  }


  @Override
  public boolean seek( long position )
  {
    return false;
  }


  @Override
  public void advance( long amount )
  {
  }


  @Override
  public boolean isSeekable()
  {
    return false;
  }


  @Override
  public int next( int bits )
  {
    return (int) nextl( bits );
  }


  @Override
  public byte[] next()
  {
  byte ret[] = new byte[blockSize()];

    next( ret );

    return ret;
  }


  @Override
  public void next( byte b[] )
  {
    next( b, 0, b.length );
  }


  @Override 
  public void next( byte b[], int offset, int length )
  {
  int amount = Math.max( 8, blockSize());

    while( length > 0 )
    {
      put( nextl( 8*amount ), b, offset, Math.min( length, amount ));

      offset += amount;
      length -= amount;
    }
  }


  protected void put( long datum, byte b[], int offset, int length )
  {
    for (int i=0; i<length; i++)
      b[offset+i] = (byte) (datum >>> (8*(length-i-1)));
  }


  protected long get( byte b[], int offset, int length )
  {
  long ret = 0L;
  
    for (int i=0; i<length; i++)
    {
      ret <<= 8;
      ret |= b[offset+i] & 0xff;
    }

    return ret;
  }


  public void setState( long seed )
  {
  byte state[] = new byte[8];

    for (int i=0; i<state.length; i++)
      state[i] = (byte) (seed >>> (56-i*8));

    setState( state );
  }


  protected IRNG deepCopy( IRNG target )
  {
    // TODO Auto-generated method stub
    return null;
  }
}


class RNGFromByteStream extends RNG
{
IByteStream byteStream;

  protected RNGFromByteStream( IByteStream byteStream )
  {
    this.byteStream = byteStream;
  }


  @Override
  public int blockSize()
  {
    return 1;
  }


  @Override
  public long nextl( int bits )
  {
  int i;
  long ret = 0L;

    for (i=0; i<bits; i+=8)
    {
      ret <<= 8;
      ret |= byteStream.next();
    }

    return ret >>> (i-bits);
  }


  @Override
  public void next( byte b[], int offset, int length )
  {
    for (int i=0; i<length; i++)
      b[offset+i] = (byte) byteStream.next();
  }


  @Override
  public IRNG deepCopy()
  {
    return new RNGFromByteStream( byteStream.deepCopy());
  }


  @Override
  public void setState( byte[] b )
  {
//    byteStream.setState( b );
  }
}

