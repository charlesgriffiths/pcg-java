package pcg.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class RNGStream extends InputStream implements ISeekableRNG
{
ISeekableRNG source;
byte buffer[];
int position;
RNGStream mark; // read only, deep copy to overwrite unless from another mark


  private RNGStream() {}


  public RNGStream( ISeekableRNG rng )
  {
    source = rng;
    buffer = new byte[rng.blockSize()];
    position = buffer.length;
  }


  public RNGStream split()
  {
    return copyTo( new RNGStream() );
  }


  public RNGStream copyTo( RNGStream target )
  {
    deepCopy( target );

    return target;
  }


  @Override
  public int read() throws IOException
  {
    position++;
    if (position >= buffer.length)
    {
      source.next( buffer );
      position = 0;
    }

    return buffer[position];
  }


  @Override
  public int available() { return Integer.MAX_VALUE; }


  @Override
  public void close() { mark = null; }


  @Override
  public void mark( int readlimit )
  {
    mark = split();
    mark.mark = mark;
  }


  @Override
  public boolean markSupported() { return true; }


  @Override
  public void reset()
  {
    mark.copyTo( this );
  }


  @Override
  public long skip( long n )
  {
  long amount = n;

    while( amount > 0 && position < buffer.length )
    {
      amount--;
      position++;
    }

    source.advance( amount / buffer.length );
    source.next( buffer );
    position = (int) (amount % buffer.length);

    return n;
  }


  @Override
  public void setState( byte[] b )
  {
    source.setState( b );
  }


  @Override
  public void seek( long position )
  {
    source.seek( position / source.blockSize() );
    skip( position % source.blockSize() );
  }


  @Override
  public void advance( long amount )
  {
    skip( amount );
  }


  @Override
  public int blockSize()
  {
    return 1;
  }


  @Override
  public long skipBytes( long amount )
  {
    return skip( amount );
  }


  @Override
  public byte next8()
  {
  byte b = 0;

    try
    {
      b = (byte) read();
    }
    catch (IOException e)  // exception not possible
    {
      e.printStackTrace();
    }

    return b;
  }


  @Override
  public short next16()
  {
    return (short) (((int)next8() << 8) | ((int)next8() & 0xff));
  }


  @Override
  public int next32()
  {
    return ((int)next16() << 16) | ((int)next16() & 0xffff);
  }


  @Override
  public long next64()
  {
    return ((long)next32() << 32) | ((long)next32() & 0xffffffff);
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
      b[i+offset] = next8();
  }


  protected void deepCopy( ISeekableRNG target )
  {
    if (target instanceof RNGStream)
    {
    RNGStream rs = (RNGStream) target;
    
      rs.source = source.deepCopy();
      rs.buffer = Arrays.copyOf( buffer, buffer.length );
      rs.position = position;
      rs.mark = mark;
    }
  }


  @Override
  public ISeekableRNG deepCopy()
  {
  ISeekableRNG target = new RNGStream();

    deepCopy( target );

    return target;
  }
}

