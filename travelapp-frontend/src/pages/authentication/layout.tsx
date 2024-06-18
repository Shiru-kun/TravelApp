import { ReactNode } from "react"
import styles from '../../styles/pages/authentication/auth.module.scss'

type Props = {
    children: ReactNode
  }
const Layout=({ children }: Props)=> {
  return (
    <>
     <div className={styles.layout}>
      <div className={styles.imageWrapper} />
      <div className={styles.formWrapper}>
        {children}
      
      </div>
    </div>
    </> 
  );
  }
  export default Layout;
