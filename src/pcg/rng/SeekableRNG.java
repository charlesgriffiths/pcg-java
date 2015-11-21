package pcg.rng;


public abstract class SeekableRNG implements ISeekableRNG
{
  @Override
  abstract public void setState( byte b[] );


  @Override
  abstract public boolean seek( long position );


  @Override
  abstract public void advance( long amount );


  @Override
  public int blockSize()
  {
    return 4;
  }


  @Override
  public long skipBytes( long amount )
  {
    advance( amount / blockSize() );

    return amount % blockSize();
  }


  @Override
  public byte next8()
  {
    return (byte) (next32() >> 24);
  }


  @Override
  public short next16()
  {
    return (short) (next32() >> 16);
  }


  @Override
  abstract public int next32();


  @Override
  public long next64()
  {
    return ((long) next32() << 32) | ((long) next32() & 0xffffffff);
  }


  @Override
  public void next( byte b[] )
  {
    next( b, 0, b.length );
  }


  protected void copy( long source, byte b[], int offset, int length )
  {
    for( int i=0; i<8 && length > 0; i++, length-- )
    {
      b[offset+i] = (byte) (source>>56);
      source <<= 8;
    }
  }


  @Override
  public void next( byte b[], int offset, int length )
  {
    while( length >= 8 )
    {
      copy( next64(), b, offset, 8 );

      offset += 8;
      length -= 8;
    }

    switch( blockSize() )
    {
      case 8:
        copy( next64(), b, offset, length );
        break;

      case 4:
        if (length > 4)
          copy( next64(), b, offset, length );
        else
          copy( (long)next32()<<32, b, offset, length );
        break;

      case 2:
        for (int i=0; i < length; i+=2 )
          copy( (long)next16()<<48, b, offset+i, Math.min( 2, length-i ) );
        break;

      default:
        for (int i=0; i < length; i++)
          b[offset+i] = next8();
    }
  }


  @Override
  abstract public ISeekableRNG deepCopy();

  abstract protected ISeekableRNG deepCopy( ISeekableRNG target );


  @Override
  public void setState( long seed )
  {
  byte b[] = new byte[8];

    for (int i=0; i<b.length; i++)
    {
      b[i] = (byte)(seed >> 8*i);
    }

    setState( b );
  }

}

