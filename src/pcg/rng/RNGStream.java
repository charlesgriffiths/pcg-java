package pcg.rng;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class RNGStream extends InputStream
{
ISeekableRNG source;
byte buffer[];
int position;
RNGStream mark;


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
    target.source = source.deepCopy();
    target.buffer = Arrays.copyOf( buffer, buffer.length );
    target.position = position;
    target.mark = mark;

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
}

